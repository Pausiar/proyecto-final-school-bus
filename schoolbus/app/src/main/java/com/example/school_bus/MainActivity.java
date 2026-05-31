package com.example.school_bus;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.activities.DashboardActivity;
import com.example.school_bus.activities.Login;
import com.example.school_bus.activities.Register;
import com.example.school_bus.session.SessionManager;
import com.example.school_bus.utils.PermisosUtils;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // solicitar notificaciones push si es necesario
        if (!PermisosUtils.tienePermisosFMC(this)) {
            PermisosUtils.solicitarPermisosFMC(this, 200);
        }

        if (openRoleHome()) {
            return;
        }

        setContentView(R.layout.activity_main);

        Button btnLogin    = findViewById(R.id.btnLogin);
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

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            SessionManager.clear(this);
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
        return DashboardActivity.class;
    }
}