package com.example.school_bus.session;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.school_bus.models.User;

public final class SessionManager {

    private static final String PREFS_NAME = "session";
    private static final String KEY_UID = "uid";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_ROLE = "role";

    private SessionManager() {
    }

    public static void saveUser(@NonNull Context context, @NonNull User user) {
        getPreferences(context)
                .edit()
                .putString(KEY_UID, safeValue(user.getId()))
                .putString(KEY_NAME, safeValue(user.getNombre()))
                .putString(KEY_SURNAME, safeValue(user.getApellidos()))
                .putString(KEY_EMAIL, safeValue(user.getEmail()))
                .putString(KEY_ROLE, safeValue(user.getRol()))
                .apply();
    }

    public static String getEmail(@NonNull Context context) {
        return getPreferences(context).getString(KEY_EMAIL, "");
    }

    public static String getRole(@NonNull Context context) {
        return getPreferences(context).getString(KEY_ROLE, "");
    }

    public static String getDisplayName(@NonNull Context context) {
        String name = getPreferences(context).getString(KEY_NAME, "");
        String surname = getPreferences(context).getString(KEY_SURNAME, "");
        return (name + " " + surname).trim();
    }

    public static void clear(@NonNull Context context) {
        getPreferences(context).edit().clear().apply();
    }

    private static SharedPreferences getPreferences(@NonNull Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    private static String safeValue(String value) {
        return value == null ? "" : value;
    }
}