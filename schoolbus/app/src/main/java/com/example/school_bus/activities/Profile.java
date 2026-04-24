package com.example.school_bus.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.school_bus.R;
import com.example.school_bus.database.FirebaseHelper;

import java.util.Map;

public class Profile extends AppCompatActivity {

    TextView tvName, tvEmail, tvRole;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvProfileName);
        tvEmail = findViewById(R.id.tvProfileEmail);
        tvRole = findViewById(R.id.tvProfileRole);

        firebaseHelper = new FirebaseHelper();

        String email = getSharedPreferences("session", MODE_PRIVATE)
                .getString("email", "");

        firebaseHelper.getUserByEmail(email, new FirebaseHelper.OnDataListener() {
            @Override
            public void onSuccess(Map<String, Object> data) {
                tvName.setText((String) data.get("name"));
                tvEmail.setText((String) data.get("email"));
                tvRole.setText((String) data.get("role"));
            }

            @Override
            public void onFailure(String error) {
                tvName.setText("Error al cargar perfil");
            }
        });
    }
}