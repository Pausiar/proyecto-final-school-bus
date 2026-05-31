package com.example.school_bus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.school_bus.R;
import com.example.school_bus.session.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

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

        String displayName = name.isEmpty() ? getString(R.string.default_user_name) : name;
        tvHello.setText(getString(R.string.dashboard_hello, displayName));
        tvRole.setText(role.isEmpty() ? getString(R.string.default_user_role) : role.toUpperCase(Locale.ROOT));

        boolean isDriver = "driver".equalsIgnoreCase(role);
        btnMap.setVisibility(isDriver ? View.VISIBLE : View.GONE);
        btnStudents.setVisibility(isDriver ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean isDriver = "driver".equalsIgnoreCase(SessionManager.getRole(this));
        menu.findItem(R.id.action_stops).setVisible(isDriver);
        menu.findItem(R.id.action_generate_code).setVisible(isDriver);
        menu.findItem(R.id.action_use_code).setVisible(!isDriver);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_notifications) {
            startActivity(new Intent(this, NotificationActivity.class));
            return true;
        } else if (id == R.id.action_stops) {
            startActivity(new Intent(this, GestionarParadasActivity.class));
            return true;
        } else if (id == R.id.action_generate_code) {
            startActivity(new Intent(this, GenerarCodigoActivity.class));
            return true;
        } else if (id == R.id.action_use_code) {
            startActivity(new Intent(this, UsarCodigoActivity.class));
            return true;
        } else if (id == R.id.action_about) {
            startActivity(new Intent(this, About.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}