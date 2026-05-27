package com.example.school_bus.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.school_bus.MainActivity;
import com.example.school_bus.R;

public class ProximidadReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "proximidad_channel";
    private static final int NOTIFICATION_ID = 3001;
    private static final String ACTION_PROXIMIDAD = "com.example.school_bus.PROXIMIDAD";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            return;
        }

        if (ACTION_PROXIMIDAD.equals(intent.getAction())) {
            String stopName = intent.getStringExtra("stopName");
            String busName = intent.getStringExtra("busName");
            int distancia = intent.getIntExtra("distancia", 0);

            mostrarNotificacion(context, stopName, busName, distancia);
        }
    }

    private void mostrarNotificacion(Context context, String stopName, String busName, int distancia) {
        crearCanal(context);

        String titulo = "¡Bus aproximándose!";
        String mensaje = generarMensaje(stopName, busName, distancia);

        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            flags |= PendingIntent.FLAG_IMMUTABLE;
        }
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, flags);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private String generarMensaje(String stopName, String busName, int distancia) {
        StringBuilder mensaje = new StringBuilder();

        if (busName != null && !busName.isEmpty()) {
            mensaje.append("El bus ").append(busName);
        } else {
            mensaje.append("El bus");
        }

        if (stopName != null && !stopName.isEmpty()) {
            mensaje.append(" se aproxima a la parada ").append(stopName);
        } else {
            mensaje.append(" se aproxima");
        }

        if (distancia > 0) {
            mensaje.append(" (a ").append(distancia).append(" metros)");
        }

        return mensaje.toString();
    }

    private void crearCanal(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Notificaciones de Proximidad",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notificaciones cuando el bus se aproxima a una parada");

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    public static Intent crearIntentProximidad(Context context, String stopName, String busName, int distancia) {
        Intent intent = new Intent(context, ProximidadReceiver.class);
        intent.setAction(ACTION_PROXIMIDAD);
        intent.putExtra("stopName", stopName);
        intent.putExtra("busName", busName);
        intent.putExtra("distancia", distancia);
        return intent;
    }
}

