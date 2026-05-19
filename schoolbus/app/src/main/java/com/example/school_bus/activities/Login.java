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

        String error = ValidationUtils.validarLogin(email, password);
        if (error != null) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            return;
        }

        if (!NetworkUtils.hayConexion(this)) {
            Toast.makeText(this, "Sin conexión a internet", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseHelper.checkLogin(email, password, new FirebaseHelper.OnCompleteListener() {
            @Override
            public void onSuccess(String uid) {
                // Obtener datos del usuario desde Firestore y guardar en SessionManager
                FirebaseFirestore.getInstance()
                        .collection("users")
                        .document(uid)
                        .get()
                        .addOnSuccessListener(doc -> {
                            if (doc.exists()) {
                                String nombre = doc.getString("nombre") != null ? doc.getString("nombre") : "";
                                String apellido = doc.getString("apellidos") != null ? doc.getString("apellidos") : "";
                                String rol = doc.getString("rol") != null ? doc.getString("rol") : "estudiante";

                                getSharedPreferences("session", MODE_PRIVATE).edit()
                                        .putString("uid", uid)
                                        .putString("name", nombre)
                                        .putString("surname", apellido)
                                        .putString("email", email)
                                        .putString("role", rol)
                                        .apply();
                            }
                            irAlDashboard();
                        })
                        .addOnFailureListener(e -> irAlDashboard());
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(Login.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void irAlDashboard() {
        Toast.makeText(this, "Login correcto", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, DashboardActivity.class));
        finish();
    }
}