package com.example.school_bus.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UsarCodigoActivity extends AppCompatActivity {

    private EditText inputCodigo;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usar_codigo);

        db = FirebaseFirestore.getInstance();
        inputCodigo = findViewById(R.id.inputCodigo);
        Button btnUsar = findViewById(R.id.btnUsarCodigo);

        btnUsar.setOnClickListener(v -> usarCodigo());
    }

    private void usarCodigo() {
        String codigo = inputCodigo.getText().toString().trim().toUpperCase();
        if (TextUtils.isEmpty(codigo)) {
            Toast.makeText(this, "introduce un codigo", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("codigos_vinculacion").document(codigo).get()
                .addOnSuccessListener(doc -> {
                    if (!doc.exists()) {
                        Toast.makeText(this, "codigo no valido", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Boolean usado = doc.getBoolean("usado");
                    if (Boolean.TRUE.equals(usado)) {
                        Toast.makeText(this, "codigo ya usado", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    marcarUsado(codigo);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "error al validar", Toast.LENGTH_SHORT).show());
    }

    private void marcarUsado(String codigo) {
        Map<String, Object> update = new HashMap<>();
        update.put("usado", true);
        update.put("usado_en", System.currentTimeMillis());

        db.collection("codigos_vinculacion").document(codigo)
                .update(update)
                .addOnSuccessListener(r -> {
                    Toast.makeText(this, "vinculacion correcta", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "no se pudo vincular", Toast.LENGTH_SHORT).show());
    }
}
