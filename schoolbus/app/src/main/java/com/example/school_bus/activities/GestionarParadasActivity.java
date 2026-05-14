package com.example.school_bus.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.example.school_bus.database.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class GestionarParadasActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private EditText inputNombreParada;
    private ArrayAdapter<String> adapter;
    private final List<String> paradas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_paradas);

        dbHelper = new DBHelper(this);
        inputNombreParada = findViewById(R.id.inputNombreParada);
        Button btnAgregarParada = findViewById(R.id.btnAgregarParada);
        ListView listParadas = findViewById(R.id.listParadas);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, paradas);
        listParadas.setAdapter(adapter);

        btnAgregarParada.setOnClickListener(v -> agregarParada());

        cargarParadas();
    }

    private void agregarParada() {
        String nombreParada = inputNombreParada.getText().toString().trim();
        if (TextUtils.isEmpty(nombreParada)) {
            Toast.makeText(this, "introduce un nombre de parada", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.insertStop(nombreParada);
        if (result > 0) {
            inputNombreParada.setText("");
            cargarParadas();
            Toast.makeText(this, "parada guardada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "no se pudo guardar", Toast.LENGTH_SHORT).show();
        }
    }

    private void cargarParadas() {
        paradas.clear();

        Cursor cursor = dbHelper.getAllStops();
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex("name");
            while (cursor.moveToNext()) {
                if (nameIndex >= 0) {
                    paradas.add(cursor.getString(nameIndex));
                }
            }
            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }
}
