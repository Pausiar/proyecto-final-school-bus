package com.example.school_bus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.example.school_bus.database.FirebaseHelper;
import com.example.school_bus.utils.NetworkUtils;
import com.example.school_bus.utils.ValidationUtils;

public class Register extends AppCompatActivity {

    EditText etName, etSurname, etEmail, etPassword;
    RadioGroup rgRol;
    Button btnRegister;
    FirebaseHelper firebaseHelper;

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
        firebaseHelper = new FirebaseHelper();

        btnRegister.setOnClickListener(v -> registerUser());

        // Link a login
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

        // Validación
        String error = ValidationUtils.validateRegistration(name, surname, email, password);
        if (error != null) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            return;
        }

        // Conexión
        if (!NetworkUtils.hasConnection(this)) {
            Toast.makeText(this, "Sin conexión a internet", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener rol seleccionado
        String role;
        int selectedId = rgRol.getCheckedRadioButtonId();
        if (selectedId == R.id.rbConductor) {
            role = "driver";
        } else if (selectedId == R.id.rbPadre) {
            role = "parent";
        } else {
            role = "student";
        }

        firebaseHelper.insertUser(name, surname, email, password, role,
                new FirebaseHelper.OnCompleteListener() {
                    @Override
                    public void onSuccess(String uid) {
                        Toast.makeText(Register.this, "Usuario registrado correctamente",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Register.this, Login.class));
                        finish();
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(Register.this, "Error al registrar: " + error,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}