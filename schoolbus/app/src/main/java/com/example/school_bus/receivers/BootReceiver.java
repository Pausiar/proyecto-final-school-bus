package com.example.school_bus.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.school_bus.services.UbicacionService;
import com.example.school_bus.session.SessionManager;
import com.example.school_bus.utils.PermisosUtils;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }

        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            reiniciarServicios(context);
        }
    }

    private void reiniciarServicios(Context context) {
        String role = SessionManager.getRole(context);

        if ("driver".equalsIgnoreCase(role)) {
            if (PermisosUtils.tienePermisosUbicacion(context)) {
                iniciarServicioUbicacion(context);
            }
        }
    }

    private void iniciarServicioUbicacion(Context context) {
        Intent serviceIntent = new Intent(context, UbicacionService.class);
        context.startService(serviceIntent);
    }
}

