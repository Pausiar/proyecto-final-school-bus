package com.example.school_bus.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.school_bus.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionarParadasActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText inputStopName;
    private ArrayAdapter<String> adapter;
    private final List<String> stops = new ArrayList<>();

    private static final String COL_STOPS = "stops";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_paradas);


        db = FirebaseFirestore.getInstance();

        inputStopName = findViewById(R.id.inputNombreParada);
        Button btnAddStop = findViewById(R.id.btnAgregarParada);
        ListView listStops = findViewById(R.id.listParadas);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stops);
        listStops.setAdapter(adapter);

        btnAddStop.setOnClickListener(v -> addStop());

        loadStops();
    }

    private void addStop() {
        String stopName = inputStopName.getText().toString().trim();
        if (TextUtils.isEmpty(stopName)) {
            Toast.makeText(this, "Enter a stop name", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> data = new HashMap<>();
        data.put("name", stopName);

        db.collection(COL_STOPS).add(data)
                .addOnSuccessListener(ref -> {
                    inputStopName.setText("");
                    loadStops();
                    Toast.makeText(this, "Stop saved", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Could not save stop", Toast.LENGTH_SHORT).show()
                );
    }

    private void loadStops() {
        stops.clear();
        db.collection(COL_STOPS).get()
                .addOnSuccessListener(query -> {
                    for (QueryDocumentSnapshot doc : query) {
                        String name = doc.getString("name");
                        if (name != null) stops.add(name);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error loading stops: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show()
                );
    }
}