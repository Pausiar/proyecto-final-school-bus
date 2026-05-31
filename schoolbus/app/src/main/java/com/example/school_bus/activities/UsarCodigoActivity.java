package com.example.school_bus.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.example.school_bus.session.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;

import java.util.Locale;
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "inicia sesión para vincularte", Toast.LENGTH_SHORT).show();
            return;
        }

        String codigo = inputCodigo.getText().toString().trim().toUpperCase(Locale.ROOT);
        if (TextUtils.isEmpty(codigo)) {
            Toast.makeText(this, "introduce un codigo", Toast.LENGTH_SHORT).show();
            return;
        }
        if (codigo.length() != 6) {
            Toast.makeText(this, "el codigo debe tener 6 caracteres", Toast.LENGTH_SHORT).show();
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
                    String driverUid = doc.getString("driverUid");
                    if (TextUtils.isEmpty(driverUid)) {
                        Toast.makeText(this, "codigo incompleto", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    marcarUsado(codigo, driverUid, user.getUid());
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "error al validar", Toast.LENGTH_SHORT).show());
    }

    private void marcarUsado(String codigo, String driverUid, String userUid) {
        DocumentReference codeRef = db.collection("codigos_vinculacion").document(codigo);
        DocumentReference userRef = db.collection("users").document(userUid);

        Map<String, Object> update = new HashMap<>();
        update.put("usado", true);
        update.put("usado_en", System.currentTimeMillis());
        update.put("usado_por", userUid);

        Map<String, Object> userUpdate = new HashMap<>();
        userUpdate.put("linkedDriverUid", driverUid);
        userUpdate.put("linked_driver_uid", driverUid);
        userUpdate.put("linkedAt", System.currentTimeMillis());

        WriteBatch batch = db.batch();
        batch.update(codeRef, update);
        batch.set(userRef, userUpdate, SetOptions.merge());

        batch.commit()
                .addOnSuccessListener(r -> {
                    SessionManager.saveLinkedDriverUid(this, driverUid);
                    Toast.makeText(this, "vinculacion correcta", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "no se pudo vincular", Toast.LENGTH_SHORT).show());
    }
}
