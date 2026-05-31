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
import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    TextView tvProfileName, tvProfileEmail, tvProfileRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        tvProfileName = findViewById(R.id.tvProfileName);
        tvProfileEmail = findViewById(R.id.tvProfileEmail);
        tvProfileRole = findViewById(R.id.tvProfileRole);

        loadProfileData();

        LinearLayout rowEditProfile = findViewById(R.id.rowEditProfile);
        rowEditProfile.setOnClickListener(v -> startActivity(new Intent(this, EditProfileActivity.class)));

        LinearLayout rowSettings = findViewById(R.id.rowSettings);
        rowSettings.setOnClickListener(v -> startActivity(new Intent(this, Settings.class)));

        LinearLayout rowLogout = findViewById(R.id.rowLogout);
        rowLogout.setOnClickListener(v -> confirmLogout());

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_profile);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_profile) {
                return true;
            } else if (id == R.id.nav_home) {
                startActivity(new Intent(this, DashboardActivity.class));
                return true;
            } else if (id == R.id.nav_map) {
                startActivity(new Intent(this, MapaConductorActivity.class));
                return true;
            } else if (id == R.id.nav_routes) {
                startActivity(new Intent(this, GestionRutasActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProfileData();
    }

    private void loadProfileData() {
        String name = SessionManager.getDisplayName(this);
        String email = SessionManager.getEmail(this);
        String role = SessionManager.getRole(this);

        tvProfileName.setText(name.isEmpty() ? "Usuario" : name);
        tvProfileEmail.setText(email.isEmpty() ? "—" : email);
        tvProfileRole.setText(role.isEmpty() ? "—" : capitalize(role));
    }

    private void confirmLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar sesión")
                .setMessage("¿Seguro que quieres cerrar sesión?")
                .setPositiveButton("Cerrar sesión", (dialog, which) -> {
                    FirebaseAuth.getInstance().signOut();
                    SessionManager.clear(this);
                    Intent intent = new Intent(this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private String capitalize(String text) {
        if (text == null || text.isEmpty()) return text;
        return text.substring(0, 1).toUpperCase(Locale.ROOT)
                + text.substring(1).toLowerCase(Locale.ROOT);
    }
}