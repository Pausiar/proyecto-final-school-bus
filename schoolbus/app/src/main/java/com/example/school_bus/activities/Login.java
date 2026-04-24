package com.example.school_bus.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.MainActivity;
import com.example.school_bus.R;
import com.example.school_bus.database.DBHelper;
import com.example.school_bus.firebase.FirebaseUserRepository;
import com.example.school_bus.models.User;
import com.example.school_bus.session.SessionManager;

public class Login extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    DBHelper dbHelper;
    FirebaseUserRepository firebaseUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        dbHelper = new DBHelper(this);
        if (FirebaseUserRepository.isAvailable(this)) {
            firebaseUserRepository = new FirebaseUserRepository(this);
        }

        btnLogin.setOnClickListener(v -> login());
    }

    private void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Email y contraseña son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        setLoading(true);

        if (firebaseUserRepository != null) {
            loginWithFirebase(email, password);
            return;
        }

        loginWithLocalDatabase(email, password);
    }

    private void loginWithFirebase(String email, String password) {
        firebaseUserRepository.loginUser(email, password, new FirebaseUserRepository.UserCallback() {
            @Override
            public void onSuccess(User user) {
                SessionManager.saveUser(Login.this, user);
                Toast.makeText(Login.this, "Login correcto", Toast.LENGTH_SHORT).show();
                openHome();
            }

            @Override
            public void onError(String message) {
                setLoading(false);
                Toast.makeText(Login.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginWithLocalDatabase(String email, String password) {
        if(dbHelper.checkLogin(email, password)){
            SessionManager.saveUser(this, getLocalUser(email));
            Toast.makeText(this, "Login correcto", Toast.LENGTH_SHORT).show();
            openHome();
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            setLoading(false);
        }
    }

    private User getLocalUser(String email) {
        User user = new User();
        Cursor cursor = dbHelper.getUserByEmail(email);

        if (cursor.moveToFirst()) {
            user.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
            user.setSurname(cursor.getString(cursor.getColumnIndexOrThrow("surname")));
            user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            user.setRole(cursor.getString(cursor.getColumnIndexOrThrow("role")));
        }

        cursor.close();
        return user;
    }

    private void openHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void setLoading(boolean isLoading) {
        btnLogin.setEnabled(!isLoading);
    }
}
