package com.example.school_bus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.activities.DashboardActivity;
import com.example.school_bus.activities.Login;
import com.example.school_bus.activities.MapaConductorActivity;
import com.example.school_bus.activities.Register;
import com.example.school_bus.session.SessionManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (openRoleHome()) {
            return;
        }

        setContentView(R.layout.activity_main);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v ->
                startActivity(new Intent(this, Login.class)));

        btnRegister.setOnClickListener(v ->
                startActivity(new Intent(this, Register.class)));
    }

    private boolean openRoleHome() {
        String email = SessionManager.getEmail(this);
        if (email.isEmpty()) {
            return false;
        }

        Intent intent = new Intent(this, resolveHomeClass(SessionManager.getRole(this)));
        intent.putExtra("email", email);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
        return true;
    }

    private Class<?> resolveHomeClass(String role) {
        String safeRole = role == null ? "" : role.trim().toLowerCase();
        switch (safeRole) {
            case "conductor":
                return MapaConductorActivity.class;
            case "estudiante":
            case "padre":
            case "tutor":
            default:
                return DashboardActivity.class;
        }
    }
}
