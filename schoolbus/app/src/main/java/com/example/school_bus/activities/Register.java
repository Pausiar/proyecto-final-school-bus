package com.example.school_bus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.example.school_bus.firebase.FirebaseUserRepository;
import com.example.school_bus.models.User;
import com.example.school_bus.session.SessionManager;
import com.example.school_bus.utils.NetworkUtils;
import com.example.school_bus.utils.ValidationUtils;

public class Register extends AppCompatActivity {

    private EditText etName, etSurname, etEmail, etPassword;
    private RadioGroup rgRol;
    private Button btnRegister;
    private ProgressBar progressBar;
    private FirebaseUserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        rgRol = findViewById(R.id.rgRol);
        btnRegister = findViewById(R.id.btnRegister);
        progressBar = findViewById(R.id.progressBar);
        userRepository = new FirebaseUserRepository(this);

        btnRegister.setOnClickListener(v -> registerUser());

        TextView tvGoLogin = findViewById(R.id.tvGoLogin);
        tvGoLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, Login.class));
            finish();
        });
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        String error = ValidationUtils.validateRegistration(name, surname, email, password);
        if (error != null) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!NetworkUtils.hasConnection(this)) {
            Toast.makeText(this, "Sin conexión a internet", Toast.LENGTH_SHORT).show();
            return;
        }

        String role;
        int selectedId = rgRol.getCheckedRadioButtonId();
        if (selectedId == R.id.rbConductor) {
            role = "driver";
        } else if (selectedId == R.id.rbPadre) {
            role = "parent";
        } else {
            role = "student";
        }

        setLoading(true);
        userRepository.registerUser(name, surname, email, password, role,
                new FirebaseUserRepository.UserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        SessionManager.saveUser(Register.this, user);
                        Toast.makeText(Register.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, DashboardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        setLoading(false);
                        Toast.makeText(Register.this, error, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setLoading(boolean loading) {
        btnRegister.setEnabled(!loading);
        btnRegister.setText(loading ? "Registrando..." : "Registrarse");
        if (progressBar != null) {
            progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
    }
}