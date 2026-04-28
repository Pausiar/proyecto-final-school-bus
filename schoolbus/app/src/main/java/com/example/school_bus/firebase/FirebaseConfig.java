package com.example.school_bus.firebase;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.school_bus.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

public final class FirebaseConfig {

    private FirebaseConfig() {
    }

    public static boolean ensureInitialized(@NonNull Context context) {
        if (!FirebaseApp.getApps(context).isEmpty()) {
            return true;
        }

        if (!isConfigured(context)) {
            return false;
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApiKey(context.getString(R.string.firebase_api_key).trim())
                .setApplicationId(context.getString(R.string.firebase_application_id).trim())
                .setProjectId(context.getString(R.string.firebase_project_id).trim())
                .setDatabaseUrl(context.getString(R.string.firebase_database_url).trim())
                .build();

        FirebaseApp.initializeApp(context, options);
        return !FirebaseApp.getApps(context).isEmpty();
    }

    public static boolean isConfigured(@NonNull Context context) {
        return hasRealValue(context.getString(R.string.firebase_api_key))
                && hasRealValue(context.getString(R.string.firebase_application_id))
                && hasRealValue(context.getString(R.string.firebase_project_id))
                && hasRealValue(context.getString(R.string.firebase_database_url));
    }

    private static boolean hasRealValue(String value) {
        if (value == null) {
            return false;
        }

        String normalized = value.trim();
        return !normalized.isEmpty()
                && !normalized.startsWith("YOUR_")
                && !normalized.contains("REPLACE_ME");
    }
}