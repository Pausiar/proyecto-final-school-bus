package com.example.school_bus.firebase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.school_bus.models.User;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class FirebaseUserRepository {

    public interface UserCallback {
        void onSuccess(User user);
        void onError(String message);
    }

    private static final String COL_USERS = "users";

    private final FirebaseAuth auth;
    private final FirebaseFirestore db;

    public FirebaseUserRepository(@NonNull Context context) {
        Context applicationContext = context.getApplicationContext();
        if (!FirebaseConfig.ensureInitialized(applicationContext)) {
            throw new IllegalStateException("Firebase no está configurado");
        }

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public static boolean isAvailable(@NonNull Context context) {
        return FirebaseConfig.ensureInitialized(context.getApplicationContext());
    }

    public boolean hasActiveSession() {
        return auth.getCurrentUser() != null;
    }

    public void signOut() {
        auth.signOut();
    }

    public void registerUser(String name, String surname, String email, String password,
                             String role, UserCallback callback) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        callback.onError(mapAuthError(task.getException()));
                        return;
                    }

                    FirebaseUser currentUser = auth.getCurrentUser();
                    if (currentUser == null) {
                        callback.onError("No se pudo recuperar el usuario creado en Firebase");
                        return;
                    }

                    User user = buildUser(currentUser.getUid(), name, surname, email, role);
                    db.collection(COL_USERS).document(currentUser.getUid())
                            .set(user)
                            .addOnSuccessListener(unused -> callback.onSuccess(user))
                            .addOnFailureListener(error -> {
                                currentUser.delete();
                                callback.onError("Se creó la cuenta, pero falló el guardado del perfil");
                            });
                });
    }

    public void loginUser(String email, String password, UserCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        callback.onError(mapAuthError(task.getException()));
                        return;
                    }

                    fetchCurrentUserProfile(callback);
                });
    }

    public void fetchCurrentUserProfile(UserCallback callback) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            callback.onError("No hay una sesión activa en Firebase");
            return;
        }

        db.collection(COL_USERS).document(currentUser.getUid())
                .get()
                .addOnSuccessListener(snapshot -> {
                    User user = snapshot.toObject(User.class);
                    if (user == null) {
                        User fallbackUser = new User();
                        fallbackUser.setId(currentUser.getUid());
                        fallbackUser.setEmail(currentUser.getEmail());
                        fallbackUser.setRole("student");
                        fallbackUser.setActive(true);
                        callback.onSuccess(fallbackUser);
                        return;
                    }

                    if (user.getId() == null || user.getId().trim().isEmpty()) {
                        user.setId(currentUser.getUid());
                    }
                    if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                        user.setEmail(currentUser.getEmail());
                    }
                    if (user.getLinkedDriverUid() == null || user.getLinkedDriverUid().trim().isEmpty()) {
                        user.setLinkedDriverUid(snapshot.getString("linked_driver_uid"));
                    }
                    callback.onSuccess(user);
                })
                .addOnFailureListener(error -> callback.onError("No se pudo cargar el perfil desde Firebase"));
    }

    public void updateUserProfile(String name, String surname, String phone, UserCallback callback) {
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser == null) {
            callback.onError("No hay una sesión activa en Firebase");
            return;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("surname", surname);
        data.put("phone", phone);

        db.collection(COL_USERS).document(currentUser.getUid())
                .set(data, SetOptions.merge())
                .addOnSuccessListener(unused -> {
                    User updatedUser = new User();
                    updatedUser.setId(currentUser.getUid());
                    updatedUser.setName(name);
                    updatedUser.setSurname(surname);
                    updatedUser.setPhone(phone);
                    callback.onSuccess(updatedUser);
                })
                .addOnFailureListener(error -> callback.onError("No se pudo actualizar el perfil"));
    }

    private User buildUser(String id, String name, String surname, String email, String role) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setRole(role);
        user.setActive(true);
        return user;
    }

    private String mapAuthError(Exception exception) {
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            return "La contraseña debe tener al menos 6 caracteres";
        }
        if (exception instanceof FirebaseAuthUserCollisionException) {
            return "Ya existe una cuenta con ese email";
        }
        if (exception instanceof FirebaseAuthInvalidUserException) {
            return "No existe una cuenta con ese email";
        }
        if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            return "Email o contraseña no válidos";
        }
        if (exception instanceof FirebaseNetworkException) {
            return "No hay conexión para acceder a Firebase";
        }
        if (exception instanceof FirebaseTooManyRequestsException) {
            return "Demasiados intentos. Inténtalo más tarde";
        }
        if (exception != null && exception.getLocalizedMessage() != null) {
            return exception.getLocalizedMessage();
        }
        return "No se pudo completar la operación con Firebase";
    }
}