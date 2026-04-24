package com.example.school_bus.database;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class FirebaseHelper {

    private final FirebaseFirestore db;
    private final FirebaseAuth auth;

    // Nombres de colecciones (las tablas)
    public static final String COL_USERS = "users";
    public static final String COL_STUDENTS = "students";
    public static final String COL_BUSES = "buses";
    public static final String COL_STOPS = "stops";
    public static final String COL_NOTIFICATIONS = "notifications";

    public FirebaseHelper() {
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }


    // ================= USUARIOS ==========================

    /**
     * Register con Firebase Auth + guardar datos extra en Firestore
     */
    public void insertUser(String name, String surname,
                           String email, String password,
                           String role, OnCompleteListener listener) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(result -> {
                    String uid = result.getUser().getUid();
                    Map<String, Object> user = new HashMap<>();
                    user.put("name", name);
                    user.put("surname", surname);
                    user.put("email", email);
                    user.put("role", role);

                    db.collection(COL_USERS).document(uid)
                            .set(user)
                            .addOnSuccessListener(unused -> listener.onSuccess(uid))
                            .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
                })
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    /**
     * Login con Firebase Auth
     */
    public void checkLogin(String email, String password, OnCompleteListener listener) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(r -> listener.onSuccess(r.getUser().getUid()))
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    /**
     * Obtener datos del usuario actual
     */
    public void getUserByEmail(String email, OnDataListener listener) {
        String uid = auth.getCurrentUser() != null
                ? auth.getCurrentUser().getUid() : null;
        if (uid == null) {
            listener.onFailure("No hay sesión activa");
            return;
        }

        db.collection(COL_USERS).document(uid).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) listener.onSuccess(doc.getData());
                    else listener.onFailure("Usuario no encontrado");
                })
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    // ================= ESTUDIANTES =======================
    public void insertStudent(String name, String userId,
                              String busId, String stopId,
                              OnCompleteListener listener) {
        Map<String, Object> student = new HashMap<>();
        student.put("name", name);
        student.put("user_id", userId);
        student.put("bus_id", busId);
        student.put("stop_id", stopId);

        db.collection(COL_STUDENTS).add(student)
                .addOnSuccessListener(ref -> listener.onSuccess(ref.getId()))
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    public void getAllStudents(OnListListener listener) {
        db.collection(COL_STUDENTS).get()
                .addOnSuccessListener(query -> {
                    List<Map<String, Object>> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : query) {
                        Map<String, Object> data = doc.getData();
                        data.put("id", doc.getId()); // añadimos el ID del documento
                        list.add(data);
                    }
                    listener.onSuccess(list);
                })
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    // ================= NOTIFICACIONES ====================

    public void insertNotification(String title, String message,
                                   String date, OnCompleteListener listener) {
        Map<String, Object> notif = new HashMap<>();
        notif.put("title", title);
        notif.put("message", message);
        notif.put("date", date);

        db.collection(COL_NOTIFICATIONS).add(notif)
                .addOnSuccessListener(ref -> listener.onSuccess(ref.getId()))
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }

    public void getAllNotifications(OnListListener listener) {
        db.collection(COL_NOTIFICATIONS)
                .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(query -> {
                    List<Map<String, Object>> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : query) {
                        Map<String, Object> data = doc.getData();
                        data.put("id", doc.getId());
                        list.add(data);
                    }
                    listener.onSuccess(list);
                })
                .addOnFailureListener(e -> listener.onFailure(e.getMessage()));
    }


    // ================= CALLBACKS =========================

    public interface OnCompleteListener {
        void onSuccess(String id);

        void onFailure(String error);
    }

    public interface OnDataListener {
        void onSuccess(Map<String, Object> data);

        void onFailure(String error);
    }

    public interface OnListListener {
        void onSuccess(List<Map<String, Object>> list);

        void onFailure(String error);
    }
}