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
import com.example.school_bus.session.SessionManager;
import com.example.school_bus.utils.ValidationUtils;

public class EditProfileActivity extends AppCompatActivity {

    private EditText etEditName, etEditSurname, etEditPhone;
    private Button btnSaveProfile;
    private ProgressBar progressBar;
    private FirebaseUserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        etEditName = findViewById(R.id.etEditName);
        etEditSurname = findViewById(R.id.etEditSurname);
        etEditPhone = findViewById(R.id.etEditPhone);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        progressBar = findViewById(R.id.progressBar);

        userRepository = new FirebaseUserRepository(this);

        loadCurrentProfileData();

        btnSaveProfile.setOnClickListener(v -> saveProfileChanges());
    }

    private void loadCurrentProfileData() {
        String name = SessionManager.getDisplayName(this);
        String surname = SessionManager.getSurname(this);
        String phone = SessionManager.getPhone(this);

        etEditName.setText(name);
        etEditSurname.setText(surname);
        if (phone != null && !phone.isEmpty()) {
            etEditPhone.setText(phone);
        }
    }

    private void saveProfileChanges() {
        String name = etEditName.getText().toString().trim();
        String surname = etEditSurname.getText().toString().trim();
        String phone = etEditPhone.getText().toString().trim();

        String error = ValidationUtils.validateProfile(name, surname, phone);
        if (error != null) {
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
            return;
        }

        setLoading(true);

        userRepository.updateUserProfile(name, surname, phone,
                new FirebaseUserRepository.UserCallback() {
                    @Override
                    public void onSuccess(com.example.school_bus.models.User user) {
                        setLoading(false);
                        SessionManager.setDisplayName(EditProfileActivity.this, name);
                        SessionManager.setSurname(EditProfileActivity.this, surname);
                        SessionManager.setPhone(EditProfileActivity.this, phone);
                        Toast.makeText(EditProfileActivity.this,
                                "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onError(String message) {
                        setLoading(false);
                        Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void setLoading(boolean loading) {
        btnSaveProfile.setEnabled(!loading);
        btnSaveProfile.setText(loading ? "Guardando..." : "Guardar cambios");
        if (progressBar != null) {
            progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        }
    }
}
