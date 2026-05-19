package com.example.school_bus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.school_bus.R;
import com.example.school_bus.session.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {

    private TextView tvHello, tvRole;
    private Button btnMap, btnStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Views
        tvHello    = findViewById(R.id.tvHello);
        tvRole     = findViewById(R.id.tvRole);
        btnMap     = findViewById(R.id.btnMap);
        btnStudents = findViewById(R.id.btnStudents);

        // Cargar datos del usuario desde SessionManager
        cargarDatosUsuario();

        // Botón Iniciar ruta → MapaConductorActivity
        btnMap.setOnClickListener(v -> {
            startActivity(new Intent(this, MapaConductorActivity.class));
        });

        // Botón Ver estudiantes → StudentList
        btnStudents.setOnClickListener(v -> {
            startActivity(new Intent(this, StudentList.class));
        });

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_map) {
                startActivity(new Intent(this, MapaConductorActivity.class));
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

    private void cargarDatosUsuario() {
        String nombre = SessionManager.getDisplayName(this);
        String rol    = SessionManager.getRole(this);

        tvHello.setText("Hola, " + (nombre.isEmpty() ? "Usuario" : nombre));
        tvRole.setText(rol.isEmpty() ? "USUARIO" : rol.toUpperCase());
    }
}