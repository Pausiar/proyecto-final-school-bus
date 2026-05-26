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
    private static final String KEY_PHONE = "phone";

    private SessionManager() {
    }

    public static void saveUser(@NonNull Context context, @NonNull User user) {
        getPreferences(context)
                .edit()
                .putString(KEY_UID, safeValue(user.getId()))
                .putString(KEY_NAME, safeValue(user.getName()))
                .putString(KEY_SURNAME, safeValue(user.getSurname()))
                .putString(KEY_EMAIL, safeValue(user.getEmail()))
                .putString(KEY_ROLE, safeValue(user.getRole()))
                .putString(KEY_PHONE, safeValue(user.getPhone()))
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

    public static String getSurname(@NonNull Context context) {
        return getPreferences(context).getString(KEY_SURNAME, "");
    }

    public static String getPhone(@NonNull Context context) {
        return getPreferences(context).getString(KEY_PHONE, "");
    }

    public static void setDisplayName(@NonNull Context context, String name) {
        getPreferences(context).edit().putString(KEY_NAME, safeValue(name)).apply();
    }

    public static void setSurname(@NonNull Context context, String surname) {
        getPreferences(context).edit().putString(KEY_SURNAME, safeValue(surname)).apply();
    }

    public static void setPhone(@NonNull Context context, String phone) {
        getPreferences(context).edit().putString(KEY_PHONE, safeValue(phone)).apply();
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