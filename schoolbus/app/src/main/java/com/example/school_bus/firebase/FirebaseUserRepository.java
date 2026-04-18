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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUserRepository {

    public interface UserCallback {
        void onSuccess(User user);
        void onError(String message);
    }

    private final FirebaseAuth auth;
    private final DatabaseReference usersReference;

    public FirebaseUserRepository(@NonNull Context context) {
        Context applicationContext = context.getApplicationContext();
        if (!FirebaseConfig.ensureInitialized(applicationContext)) {
            throw new IllegalStateException("Firebase no está configurado");
        }

        auth = FirebaseAuth.getInstance();
        usersReference = FirebaseDatabase.getInstance().getReference("users");
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

                    User user = new User(currentUser.getUid(), name, surname, email, role);
                    usersReference.child(currentUser.getUid())
                            .setValue(user)
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

        usersReference.child(currentUser.getUid())
                .get()
                .addOnSuccessListener(snapshot -> {
                    User user = snapshot.getValue(User.class);
                    if (user == null) {
                        User fallbackUser = new User();
                        fallbackUser.setUid(currentUser.getUid());
                        fallbackUser.setEmail(currentUser.getEmail());
                        fallbackUser.setRole("estudiante");
                        callback.onSuccess(fallbackUser);
                        return;
                    }

                    if (user.getUid() == null || user.getUid().trim().isEmpty()) {
                        user.setUid(currentUser.getUid());
                    }
                    if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                        user.setEmail(currentUser.getEmail());
                    }
                    callback.onSuccess(user);
                })
                .addOnFailureListener(error -> callback.onError("No se pudo cargar el perfil desde Firebase"));
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