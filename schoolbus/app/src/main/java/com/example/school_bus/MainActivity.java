package com.example.school_bus;
import com.example.school_bus.activities.Login;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.activities.Login;
import com.example.school_bus.activities.Register;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(v ->
                startActivity(new Intent(this, Login.class)));

        btnRegister.setOnClickListener(v ->
                startActivity(new Intent(this, Register.class)));
    }
}
