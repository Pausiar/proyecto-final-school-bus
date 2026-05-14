package com.example.school_bus.activities;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.school_bus.R;

public class Settings extends AppCompatActivity {

    private static final String PREFS_NAME = "settings";
    private static final String KEY_NOTIFICATIONS = "notifications_enabled";
    private static final String KEY_INTERVAL = "location_interval";
    private static final String KEY_RADIUS = "notification_radius";

    private Switch switchNotifications;
    private SeekBar seekInterval, seekRadius;
    private TextView tvIntervalValue, tvRadiusValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Views
        switchNotifications = findViewById(R.id.switchNotifications);
        seekInterval = findViewById(R.id.seekInterval);
        seekRadius = findViewById(R.id.seekRadius);
        tvIntervalValue = findViewById(R.id.tvIntervalValue);
        tvRadiusValue = findViewById(R.id.tvRadiusValue);

        // Cargar preferencias guardadas
        cargarPreferencias();

        // Switch notificaciones
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            guardarBoolean(KEY_NOTIFICATIONS, isChecked);
        });

        // SeekBar intervalo de ubicación
        seekInterval.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int valor = Math.max(10, progress); // mínimo 10 segundos
                tvIntervalValue.setText(valor + " segundos");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                guardarInt(KEY_INTERVAL, seekBar.getProgress());
            }
        });

        // SeekBar radio de notificación
        seekRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int valor = Math.max(100, progress); // mínimo 100 metros
                tvRadiusValue.setText(valor + " metros");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                guardarInt(KEY_RADIUS, seekBar.getProgress());
            }
        });
    }

    private void cargarPreferencias() {
        boolean notif = getPrefs().getBoolean(KEY_NOTIFICATIONS, true);
        int intervalo = getPrefs().getInt(KEY_INTERVAL, 40);
        int radio = getPrefs().getInt(KEY_RADIUS, 650);

        switchNotifications.setChecked(notif);

        seekInterval.setProgress(intervalo);
        tvIntervalValue.setText(intervalo + " segundos");

        seekRadius.setProgress(radio);
        tvRadiusValue.setText(radio + " metros");
    }

    private void guardarBoolean(String key, boolean value) {
        getPrefs().edit().putBoolean(key, value).apply();
    }

    private void guardarInt(String key, int value) {
        getPrefs().edit().putInt(key, value).apply();
    }

    private android.content.SharedPreferences getPrefs() {
        return getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }
}