package com.example.school_bus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.example.school_bus.database.FirebaseHelper;
import com.example.school_bus.utils.NetworkUtils;
import com.example.school_bus.utils.ValidationUtils;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        firebaseHelper = new FirebaseHelper();

        btnLogin.setOnClickListener(v -> login());

        TextView tvGoRegister = findViewById(R.id.tvGoRegister);
        tvGoRegister.setOnClickListener(v ->
                startActivity(new Intent(Login.this, Register.class))
        );
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

        firebaseHelper.checkLogin(email, password, new FirebaseHelper.OnCompleteListener() {
            @Override
            public void onSuccess(String uid) {
                FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(uid)
                        .get()
                        .addOnSuccessListener(doc -> {
                            if (doc.exists()) {
                                String name    = doc.getString("name") != null ? doc.getString("name") : "";
                                String surname = doc.getString("surname") != null ? doc.getString("surname") : "";
                                String role    = doc.getString("role") != null ? doc.getString("role") : "student";

                                getSharedPreferences("session", MODE_PRIVATE).edit()
                                        .putString("uid", uid)
                                        .putString("name", name)
                                        .putString("surname", surname)
                                        .putString("email", email)
                                        .putString("role", role)
                                        .apply();

                                // ✅ Solo navegamos cuando el rol ya está guardado
                                goToDashboard();

                            } else {
                                Toast.makeText(Login.this,
                                        "Usuario no encontrado en base de datos",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(Login.this,
                                    "Error al obtener datos del usuario",
                                    Toast.LENGTH_SHORT).show();
                        });
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(Login.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToDashboard() {
        Toast.makeText(this, "Login correcto", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}