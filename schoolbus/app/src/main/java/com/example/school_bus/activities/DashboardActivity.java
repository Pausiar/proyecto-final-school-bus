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
    private Button btnMap, btnStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvHello     = findViewById(R.id.tvHello);
        tvRole      = findViewById(R.id.tvRole);
        btnMap      = findViewById(R.id.btnMap);
        btnStudents = findViewById(R.id.btnStudents);

        loadUserData();

        btnMap.setOnClickListener(v ->
                startActivity(new Intent(this, MapaConductorActivity.class))
        );

        btnStudents.setOnClickListener(v ->
                startActivity(new Intent(this, StudentList.class))
        );

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

    private void loadUserData() {
        String name = SessionManager.getDisplayName(this);
        String role = SessionManager.getRole(this);

        tvHello.setText("Hola, " + (name.isEmpty() ? "Usuario" : name));
        tvRole.setText(role.isEmpty() ? "USUARIO" : role.toUpperCase());

        boolean isDriver = "driver".equalsIgnoreCase(role);
        btnMap.setVisibility(isDriver ? View.VISIBLE : View.GONE);
        btnStudents.setVisibility(isDriver ? View.VISIBLE : View.GONE);
    }
}