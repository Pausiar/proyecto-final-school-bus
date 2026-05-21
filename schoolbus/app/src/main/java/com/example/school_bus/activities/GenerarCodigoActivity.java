package com.example.school_bus.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GenerarCodigoActivity extends AppCompatActivity {

    private TextView txtCodigo;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generar_codigo);

        db = FirebaseFirestore.getInstance();
        txtCodigo = findViewById(R.id.txtCodigoGenerado);
        Button btnGenerar = findViewById(R.id.btnGenerarCodigo);

        btnGenerar.setOnClickListener(v -> generarCodigo());
    }

    private void generarCodigo() {
        String codigo = nuevoCodigo();

        Map<String, Object> data = new HashMap<>();
        data.put("codigo", codigo);
        data.put("usado", false);
        data.put("creado", System.currentTimeMillis());

        db.collection("codigos_vinculacion")
                .document(codigo)
                .set(data)
                .addOnSuccessListener(r -> txtCodigo.setText(codigo))
                .addOnFailureListener(e ->
                        Toast.makeText(this, "no se pudo generar", Toast.LENGTH_SHORT).show());
    }

    private String nuevoCodigo() {
        // codigo alfanumerico de 6 caracteres
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(r.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
