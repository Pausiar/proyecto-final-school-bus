package com.example.school_bus.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.school_bus.R;
import com.example.school_bus.firebase.FirebaseUserRepository;
import com.example.school_bus.models.User;
import com.example.school_bus.session.SessionManager;
import com.example.school_bus.utils.ValidationUtils;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etSurname;
    private EditText etPhone;
    private Button btnSave;
    private ProgressBar progressBar;
    private FirebaseUserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        etName = findViewById(R.id.etProfileName);
        etSurname = findViewById(R.id.etProfileSurname);
        etPhone = findViewById(R.id.etProfilePhone);
        btnSave = findViewById(R.id.btnSaveProfile);
        progressBar = findViewById(R.id.progressBar);
        userRepository = new FirebaseUserRepository(this);

        etName.setText(SessionManager.getName(this));
        etSurname.setText(SessionManager.getSurname(this));
        etPhone.setText(SessionManager.getPhone(this));

        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void saveProfile() {
        String name = etName.getText().toString().trim();
        String surname = etSurname.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        String error = ValidationUtils.validateProfile(name, surname, phone);
        if (error != null) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            return;
        }

        setLoading(true);
        userRepository.updateUserProfile(name, surname, phone, new FirebaseUserRepository.UserCallback() {
            @Override
            public void onSuccess(User user) {
                SessionManager.saveProfile(EditProfileActivity.this, name, surname, phone);
                Toast.makeText(EditProfileActivity.this, "perfil actualizado", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String message) {
                setLoading(false);
                Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setLoading(boolean loading) {
        btnSave.setEnabled(!loading);
        btnSave.setText(loading ? "Guardando..." : "Guardar cambios");
        if (progressBar != null) {
            progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
    }
}