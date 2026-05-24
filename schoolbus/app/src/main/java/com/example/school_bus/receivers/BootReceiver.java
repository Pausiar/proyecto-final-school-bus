package com.example.school_bus.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.school_bus.services.UbicacionService;
import com.example.school_bus.session.SessionManager;
import com.example.school_bus.utils.PermisosUtils;

/**
 * BroadcastReceiver que se activa cuando el dispositivo se reinicia (BOOT_COMPLETED).
 * Reinicia automáticamente los servicios requeridos si el usuario es un conductor y tenía
 * servicios activos antes del reinicio.
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }

        // Si es el intent de reinicio del dispositivo
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            reiniciarServicios(context);
        }
    }

    /**
     * Reinicia los servicios según el rol del usuario.
     * Si el usuario es conductor, reinicia el servicio de ubicación.
     */
    private void reiniciarServicios(Context context) {
        String role = SessionManager.getRole(context);

        // Si el usuario es conductor y tiene permisos, reinicia el servicio de ubicación
        if ("driver".equalsIgnoreCase(role)) {
            if (PermisosUtils.tienePermisosUbicacion(context)) {
                iniciarServicioUbicacion(context);
            }
        }
    }

    /**
     * Inicia el servicio de ubicación para conductores.
     */
    private void iniciarServicioUbicacion(Context context) {
        Intent serviceIntent = new Intent(context, UbicacionService.class);
        context.startService(serviceIntent);
    }
}

