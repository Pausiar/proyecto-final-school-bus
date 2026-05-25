package com.example.school_bus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.school_bus.R;
import com.example.school_bus.session.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvHello, tvRole;
    private Button btnMap, btnStudents, btnVerBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvHello      = findViewById(R.id.tvHello);
        tvRole       = findViewById(R.id.tvRole);
        btnMap       = findViewById(R.id.btnMap);
        btnStudents  = findViewById(R.id.btnStudents);
        btnVerBus    = findViewById(R.id.btnVerBus);

        loadUserData();

        // Conductor
        btnMap.setOnClickListener(v ->
                startActivity(new Intent(this, MapaConductorActivity.class))
        );
        btnStudents.setOnClickListener(v ->
                startActivity(new Intent(this, StudentList.class))
        );

        // Padre / Estudiante
        btnVerBus.setOnClickListener(v ->
                startActivity(new Intent(this, MapaEstudianteActivity.class))
        );

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_home);

        String role = SessionManager.getRole(this);
        boolean isDriver = "driver".equalsIgnoreCase(role);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_map) {
                // Mapa según el rol
                if (isDriver) {
                    startActivity(new Intent(this, MapaConductorActivity.class));
                } else {
                    startActivity(new Intent(this, MapaEstudianteActivity.class));
                }
                return true;
            } else if (id == R.id.nav_routes) {
                startActivity(new Intent(this, GestionRutasActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }
            return false;
        });
    }

    private void loadUserData() {
        String name = SessionManager.getDisplayName(this);
        String role = SessionManager.getRole(this);

        tvHello.setText("Hola, " + (name.isEmpty() ? "Usuario" : name));
        tvRole.setText(role.isEmpty() ? "USUARIO" : role.toUpperCase());

        boolean isDriver = "driver".equalsIgnoreCase(role);

        // Botones del conductor
        btnMap.setVisibility(isDriver ? View.VISIBLE : View.GONE);
        btnStudents.setVisibility(isDriver ? View.VISIBLE : View.GONE);

        // Botón del padre / estudiante
        btnVerBus.setVisibility(isDriver ? View.GONE : View.VISIBLE);
    }
}