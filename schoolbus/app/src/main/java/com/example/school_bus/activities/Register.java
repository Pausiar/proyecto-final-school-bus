package com.example.school_bus.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.school_bus.R;
import com.example.school_bus.database.FirebaseHelper;

public class Register extends AppCompatActivity {

    EditText etName, etSurname, etEmail, etPassword;
    Button btnRegister;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        firebaseHelper = new FirebaseHelper();

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseHelper.insertUser(name, surname, email, password, "estudiante",
                new FirebaseHelper.OnCompleteListener() {
                    @Override
                    public void onSuccess(String uid) {
                        Toast.makeText(Register.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        finish();                          // vuelve al login
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(Register.this, "Error al registrar: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}