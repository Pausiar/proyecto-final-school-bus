package com.example.school_bus.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.school_bus.R;
import com.example.school_bus.database.FirebaseHelper;

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
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email y contraseña son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseHelper.checkLogin(email, password, new FirebaseHelper.OnCompleteListener() {
            @Override
            public void onSuccess(String uid) {
                Toast.makeText(Login.this, "Login correcto", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, DashboardActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(Login.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}