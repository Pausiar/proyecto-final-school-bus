package com.example.school_bus.activities;

import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.school_bus.R;

public class Settings extends AppCompatActivity {

    private static final String PREFS_NAME       = "settings";
    private static final String KEY_NOTIFICATIONS = "notifications_enabled";
    private static final String KEY_INTERVAL      = "location_interval";
    private static final String KEY_RADIUS        = "notification_radius";

    private Switch switchNotifications;
    private SeekBar seekInterval, seekRadius;
    private TextView tvIntervalValue, tvRadiusValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        switchNotifications = findViewById(R.id.switchNotifications);
        seekInterval        = findViewById(R.id.seekInterval);
        seekRadius          = findViewById(R.id.seekRadius);
        tvIntervalValue     = findViewById(R.id.tvIntervalValue);
        tvRadiusValue       = findViewById(R.id.tvRadiusValue);

        loadPreferences();

        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) ->
                saveBoolean(KEY_NOTIFICATIONS, isChecked)
        );

        seekInterval.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = Math.max(10, progress);
                tvIntervalValue.setText(value + " segundos");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saveInt(KEY_INTERVAL, seekBar.getProgress());
            }
        });

        seekRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int value = Math.max(100, progress);
                tvRadiusValue.setText(value + " metros");
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saveInt(KEY_RADIUS, seekBar.getProgress());
            }
        });
    }

    private void loadPreferences() {
        boolean notifications = getPrefs().getBoolean(KEY_NOTIFICATIONS, true);
        int interval          = getPrefs().getInt(KEY_INTERVAL, 40);
        int radius            = getPrefs().getInt(KEY_RADIUS, 650);

        switchNotifications.setChecked(notifications);

        seekInterval.setProgress(interval);
        tvIntervalValue.setText(interval + " segundos");

        seekRadius.setProgress(radius);
        tvRadiusValue.setText(radius + " metros");
    }

    private void saveBoolean(String key, boolean value) {
        getPrefs().edit().putBoolean(key, value).apply();
    }

    private void saveInt(String key, int value) {
        getPrefs().edit().putInt(key, value).apply();
    }

    private android.content.SharedPreferences getPrefs() {
        return getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }
}