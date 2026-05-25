package com.example.school_bus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.example.school_bus.firebase.FirebaseUserRepository;
import com.example.school_bus.models.User;
import com.example.school_bus.session.SessionManager;
import com.example.school_bus.utils.NetworkUtils;
import com.example.school_bus.utils.ValidationUtils;

public class Login extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    ProgressBar progressBar;
    FirebaseUserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail     = findViewById(R.id.etEmail);
        etPassword  = findViewById(R.id.etPassword);
        btnLogin    = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar); // añadir en activity_login.xml

        userRepository = new FirebaseUserRepository(this);

        btnLogin.setOnClickListener(v -> login());

        TextView tvGoRegister = findViewById(R.id.tvGoRegister);
        tvGoRegister.setOnClickListener(v ->
                startActivity(new Intent(Login.this, Register.class))
        );
    }

    private void login() {
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validación con mensajes en español
        String error = ValidationUtils.validateLogin(email, password);
        if (error != null) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!NetworkUtils.hasConnection(this)) {
            Toast.makeText(this, "Sin conexión a internet", Toast.LENGTH_SHORT).show();
            return;
        }

        // Mostrar carga y bloquear el botón para evitar doble pulsación
        setLoading(true);

        userRepository.loginUser(email, password, new FirebaseUserRepository.UserCallback() {
            @Override
            public void onSuccess(User user) {
                setLoading(false);
                SessionManager.saveUser(Login.this, user);
                goToDashboard();
            }

            @Override
            public void onError(String message) {
                setLoading(false);
                Toast.makeText(Login.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    /** Activa o desactiva el estado de carga: oculta el botón y muestra el spinner. */
    private void setLoading(boolean loading) {
        btnLogin.setEnabled(!loading);
        btnLogin.setText(loading ? "Entrando..." : "Iniciar sesión");
        if (progressBar != null) {
            progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
    }

    private void goToDashboard() {
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}