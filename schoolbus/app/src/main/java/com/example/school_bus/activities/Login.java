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

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private ProgressBar progressBar;
    private FirebaseUserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);
        userRepository = new FirebaseUserRepository(this);

        btnLogin.setOnClickListener(v -> login());

        TextView tvGoRegister = findViewById(R.id.tvGoRegister);
        tvGoRegister.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        String error = ValidationUtils.validateLogin(email, password);
        if (error != null) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!NetworkUtils.hasConnection(this)) {
            Toast.makeText(this, "Sin conexión a internet", Toast.LENGTH_SHORT).show();
            return;
        }

        setLoading(true);
        userRepository.loginUser(email, password, new FirebaseUserRepository.UserCallback() {
            @Override
            public void onSuccess(User user) {
                SessionManager.saveUser(Login.this, user);
                goToDashboard();
            }

            @Override
            public void onError(String error) {
                setLoading(false);
                Toast.makeText(Login.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        btnLogin.setEnabled(!loading);
        btnLogin.setText(loading ? "Entrando..." : "Iniciar sesión");
        if (progressBar != null) {
            progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
    }

    private void goToDashboard() {
        Toast.makeText(this, "Login correcto", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}