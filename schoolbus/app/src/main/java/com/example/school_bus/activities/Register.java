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
import com.example.school_bus.utils.NetworkUtils;
import com.example.school_bus.utils.ValidationUtils;

public class Register extends AppCompatActivity {

    EditText etName, etSurname, etEmail, etPassword;
    RadioGroup rgRol;
    Button btnRegister;
    ProgressBar progressBar;
    FirebaseUserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName      = findViewById(R.id.etName);
        etSurname   = findViewById(R.id.etSurname);
        etEmail     = findViewById(R.id.etEmail);
        etPassword  = findViewById(R.id.etPassword);
        rgRol       = findViewById(R.id.rgRol);
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
        String name     = etName.getText().toString().trim();
        String surname  = etSurname.getText().toString().trim();
        String email    = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validación con mensajes en español (corregidos en ValidationUtils)
        String error = ValidationUtils.validateRegistration(name, surname, email, password);
        if (error != null) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!NetworkUtils.hasConnection(this)) {
            Toast.makeText(this, "Sin conexión a internet", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener rol seleccionado — validación explícita si no hay ninguno marcado
        int selectedId = rgRol.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Selecciona tu rol", Toast.LENGTH_SHORT).show();
            return;
        }

        String role;
        if (selectedId == R.id.rbConductor) {
            role = "driver";
        } else if (selectedId == R.id.rbPadre) {
            role = "parent";
        } else {
            // rbEstudiante (marcado por defecto en el XML)
            role = "student";
        }

        // Mostrar carga y bloquear botón para evitar doble registro
        setLoading(true);

        userRepository.registerUser(name, surname, email, password, role,
                new FirebaseUserRepository.UserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        setLoading(false);
                        Toast.makeText(Register.this,
                                "Cuenta creada correctamente", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                        finish();
                    }

                    @Override
                    public void onError(String message) {
                        setLoading(false);
                        Toast.makeText(Register.this, message, Toast.LENGTH_LONG).show();
                    }
                });
    }

    /** Activa o desactiva el estado de carga. */
    private void setLoading(boolean loading) {
        btnRegister.setEnabled(!loading);
        btnRegister.setText(loading ? "Registrando..." : "Registrarse");
        if (progressBar != null) {
            progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
    }
}