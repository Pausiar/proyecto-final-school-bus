package com.example.school_bus.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.school_bus.R;
import com.example.school_bus.database.DBHelper;
import com.example.school_bus.firebase.FirebaseUserRepository;
import com.example.school_bus.models.User;
import com.example.school_bus.session.SessionManager;

public class Register extends AppCompatActivity {

    EditText etName, etSurname, etEmail, etPassword;
    Button btnRegister;
    DBHelper dbHelper;
    FirebaseUserRepository firebaseUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);

        dbHelper = new DBHelper(this);
        if (FirebaseUserRepository.isAvailable(this)) {
            firebaseUserRepository = new FirebaseUserRepository(this);
        }

        btnRegister.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(name.isEmpty() || surname.isEmpty() || email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        setLoading(true);

        if (firebaseUserRepository != null) {
            registerWithFirebase(name, surname, email, password);
            return;
        }

        long id = dbHelper.insertUser(name, surname, email, password, "estudiante");
        setLoading(false);
        if(id > 0){
            Toast.makeText(this, "Firebase no configurado; usuario guardado localmente", Toast.LENGTH_SHORT).show();
            finish(); // vuelve al login
        } else {
            Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
        }
    }

    private void registerWithFirebase(String name, String surname, String email, String password) {
        firebaseUserRepository.registerUser(name, surname, email, password, "estudiante",
                new FirebaseUserRepository.UserCallback() {
                    @Override
                    public void onSuccess(User user) {
                        firebaseUserRepository.signOut();
                        SessionManager.clear(Register.this);
                        setLoading(false);
                        Toast.makeText(Register.this, "Usuario registrado en Firebase", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(String message) {
                        setLoading(false);
                        Toast.makeText(Register.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setLoading(boolean isLoading) {
        btnRegister.setEnabled(!isLoading);
    }
}
