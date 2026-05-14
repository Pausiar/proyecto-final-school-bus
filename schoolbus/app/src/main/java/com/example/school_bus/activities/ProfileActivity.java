package com.example.school_bus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.school_bus.R;
import com.example.school_bus.session.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    private TextView tvProfileName, tvProfileEmail, tvProfileRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Views
        tvProfileName  = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileRole  = findViewById(R.id.tvProfileRole);

        // Cargar datos desde SessionManager
        cargarDatosPerfil();

        // Navegar a editar perfil
        LinearLayout rowEditProfile = findViewById(R.id.rowEditProfile);
        rowEditProfile.setOnClickListener(v -> {
            // TODO: abrir EditarPerfilActivity cuando esté creada
        });

        // Navegar a configuración
        LinearLayout rowSettings = findViewById(R.id.rowSettings);
        rowSettings.setOnClickListener(v -> {
            Intent intent = new Intent(this, Settings.class);
            startActivity(intent);
        });

        // Cerrar sesión
        LinearLayout rowLogout = findViewById(R.id.rowLogout);
        rowLogout.setOnClickListener(v -> confirmarCerrarSesion());

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_profile);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                startActivity(new Intent(this, DashboardActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_map) {
                startActivity(new Intent(this, MapaConductorActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_routes) {
                startActivity(new Intent(this, GestionRutasActivity.class));
                finish();
                return true;
            } else if (id == R.id.nav_profile) {
                return true;
            }
            return false;
        });
    }

    private void cargarDatosPerfil() {
        String nombre   = SessionManager.getDisplayName(this);
        String email    = SessionManager.getEmail(this);
        String rol      = SessionManager.getRole(this);

        tvProfileName.setText(nombre.isEmpty() ? "Usuario" : nombre);
        tvProfileEmail.setText(email.isEmpty() ? "—" : email);
        tvProfileRole.setText(rol.isEmpty() ? "—" : capitalizar(rol));
    }

    private void confirmarCerrarSesion() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Seguro que quieres cerrar sesión?")
                .setPositiveButton("Cerrar sesión", (dialog, which) -> {
                    SessionManager.clear(this);
                    Intent intent = new Intent(this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) return texto;
        return texto.substring(0, 1).toUpperCase() + texto.substring(1).toLowerCase();
    }
}