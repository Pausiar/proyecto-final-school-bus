package com.example.school_bus.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.example.school_bus.database.DBHelper;

public class Profile extends AppCompatActivity {
    TextView tvName, tvEmail, tvRole;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvName = findViewById(R.id.tvProfileName);
        tvEmail = findViewById(R.id.tvProfileEmail);
        tvRole = findViewById(R.id.tvProfileRole);

        dbHelper = new DBHelper(this);

        // Simulación de usuario logueado
        String email = getSharedPreferences("session", MODE_PRIVATE)
                .getString("email", "");

        Cursor c = dbHelper.getUserByEmail(email);
        if (c.moveToFirst()) {
            tvName.setText(c.getString(c.getColumnIndexOrThrow("name")));
            tvEmail.setText(c.getString(c.getColumnIndexOrThrow("email")));
            tvRole.setText(c.getString(c.getColumnIndexOrThrow("role")));
        }
        c.close();
    }
}
