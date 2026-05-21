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
    private EditText inputStopName;
    private ArrayAdapter<String> adapter;
    private final List<String> stops = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_paradas);

        dbHelper = new DBHelper(this);
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
            Toast.makeText(this, "introduce un nombre de parada", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.insertStop(stopName);
        if (result > 0) {
            inputStopName.setText("");
            loadStops();
            Toast.makeText(this, "parada guardada", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "no se pudo guardar", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadStops() {
        stops.clear();

        Cursor cursor = dbHelper.getAllStops();
        if (cursor != null) {
            int nameIndex = cursor.getColumnIndex("name");
            while (cursor.moveToNext()) {
                if (nameIndex >= 0) {
                    stops.add(cursor.getString(nameIndex));
                }
            }
            cursor.close();
        }

        adapter.notifyDataSetChanged();
    }
}