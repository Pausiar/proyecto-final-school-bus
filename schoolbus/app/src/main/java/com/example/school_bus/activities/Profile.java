package com.example.school_bus.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.example.school_bus.database.DBHelper;
import com.example.school_bus.firebase.FirebaseUserRepository;
import com.example.school_bus.models.User;
import com.example.school_bus.session.SessionManager;

public class Profile extends AppCompatActivity {
    TextView tvName, tvEmail, tvRole;
    DBHelper dbHelper;
    FirebaseUserRepository firebaseUserRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvProfileName);
        tvEmail = findViewById(R.id.tvProfileEmail);
        tvRole = findViewById(R.id.tvProfileRole);

        dbHelper = new DBHelper(this);
        showSessionData();

        if (FirebaseUserRepository.isAvailable(this)) {
            firebaseUserRepository = new FirebaseUserRepository(this);
        }

        if (firebaseUserRepository != null && firebaseUserRepository.hasActiveSession()) {
            loadFirebaseProfile();
        } else {
            loadLocalProfile();
        }
    }

    private void showSessionData() {
        String displayName = SessionManager.getDisplayName(this);
        String email = SessionManager.getEmail(this);
        String role = SessionManager.getRole(this);

        if (!displayName.isEmpty()) {
            tvName.setText(displayName);
        }
        if (!email.isEmpty()) {
            tvEmail.setText(email);
        }
        if (!role.isEmpty()) {
            tvRole.setText(role);
        }
    }

    private void loadFirebaseProfile() {
        firebaseUserRepository.fetchCurrentUserProfile(new FirebaseUserRepository.UserCallback() {
            @Override
            public void onSuccess(User user) {
                SessionManager.saveUser(Profile.this, user);
                tvName.setText(buildDisplayName(user));
                tvEmail.setText(user.getEmail());
                tvRole.setText(user.getRole());
            }

            @Override
            public void onError(String message) {
                loadLocalProfile();
            }
        });
    }

    private void loadLocalProfile() {
        String email = SessionManager.getEmail(this);
        if (email.isEmpty()) {
            return;
        }

        Cursor cursor = dbHelper.getUserByEmail(email);
        if (cursor.moveToFirst()) {
            String fullName = cursor.getString(cursor.getColumnIndexOrThrow("name")) + " "
                    + cursor.getString(cursor.getColumnIndexOrThrow("surname"));
            tvName.setText(fullName.trim());
            tvEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow("email")));
            tvRole.setText(cursor.getString(cursor.getColumnIndexOrThrow("role")));
        }
        cursor.close();
    }

    private String buildDisplayName(User user) {
        String name = user.getName() == null ? "" : user.getName().trim();
        String surname = user.getSurname() == null ? "" : user.getSurname().trim();
        String fullName = (name + " " + surname).trim();
        return fullName.isEmpty() ? "Usuario" : fullName;
    }
}
