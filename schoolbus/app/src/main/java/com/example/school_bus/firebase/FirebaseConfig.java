package com.example.school_bus.firebase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.firebase.FirebaseApp;

public final class FirebaseConfig {

    private FirebaseConfig() {
    }

    public static boolean ensureInitialized(@NonNull Context context) {
        if (!FirebaseApp.getApps(context).isEmpty()) {
            return true;
        }

        return FirebaseApp.initializeApp(context) != null || !FirebaseApp.getApps(context).isEmpty();
    }

    public static boolean isConfigured(@NonNull Context context) {
        return ensureInitialized(context);
    }
}