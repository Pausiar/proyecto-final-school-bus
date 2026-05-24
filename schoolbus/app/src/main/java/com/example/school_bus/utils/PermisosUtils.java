package com.example.school_bus.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public final class PermisosUtils {

    private PermisosUtils() {}

    public static boolean tienePermisosUbicacion(Context context) {
        boolean fine = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        boolean coarse = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
        return fine && coarse;
    }

    public static void solicitarPermisosUbicacion(Activity activity, int requestCode) {
        String[] permisos = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        ActivityCompat.requestPermissions(activity, permisos, requestCode);
    }

    public static boolean tienePermisosFMC(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    public static void solicitarPermisosFMC(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            String[] permisos = new String[]{Manifest.permission.POST_NOTIFICATIONS};
            ActivityCompat.requestPermissions(activity, permisos, requestCode);
        }
    }

    public static boolean tieneTodosLosPermisos(Context context) {
        boolean ubicacion = tienePermisosUbicacion(context);
        boolean fmc = tienePermisosFMC(context);
        return ubicacion && fmc;
    }

    public static void solicitarTodosLosPermisos(Activity activity, int requestCode) {
        List<String> lista = new ArrayList<>();
        lista.add(Manifest.permission.ACCESS_FINE_LOCATION);
        lista.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            lista.add(Manifest.permission.POST_NOTIFICATIONS);
        }

        ActivityCompat.requestPermissions(activity, lista.toArray(new String[0]), requestCode);
    }
}
