package com.example.school_bus.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school_bus.R;
import com.example.school_bus.adapters.RouteAdapter;
import com.example.school_bus.models.Route;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionRutasActivity extends AppCompatActivity implements RouteAdapter.OnRouteListener {

    private RecyclerView recyclerRoutes;
    private LinearLayout layoutEmpty;
    private RouteAdapter adapter;
    private List<Route> routes;
    private FirebaseFirestore db;

    private static final String COL_ROUTES = "routes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_rutas);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Firebase
        db = FirebaseFirestore.getInstance();

        // Views
        recyclerRoutes = findViewById(R.id.recyclerRoutes);
        layoutEmpty    = findViewById(R.id.layoutEmpty);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddRoute);

        // RecyclerView
        routes = new ArrayList<>();
        adapter = new RouteAdapter(routes, this);
        recyclerRoutes.setLayoutManager(new LinearLayoutManager(this));
        recyclerRoutes.setAdapter(adapter);

        // FAB añadir
        fabAdd.setOnClickListener(v -> mostrarDialogo(null));

        // Cargar rutas
        cargarRutas();
    }

    // ─── CARGAR rutas desde Firestore ────────────────────────────────────────
    private void cargarRutas() {
        db.collection(COL_ROUTES)
                .get()
                .addOnSuccessListener(query -> {
                    routes.clear();
                    for (QueryDocumentSnapshot doc : query) {
                        Route r = new Route();
                        r.setId(doc.getId());
                        r.setNombre(doc.getString("nombre"));
                        r.setDescripcion(doc.getString("descripcion"));
                        r.setHoraInicio(doc.getString("horaInicio"));
                        r.setHoraFin(doc.getString("horaFin"));
                        Long paradas = doc.getLong("numParadas");
                        r.setNumParadas(paradas != null ? paradas.intValue() : 0);
                        Boolean activa = doc.getBoolean("activa");
                        r.setActiva(activa != null && activa);
                        routes.add(r);
                    }
                    adapter.updateList(routes);
                    mostrarEstado();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error al cargar rutas: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show()
                );
    }

    // ─── DIÁLOGO crear / editar ───────────────────────────────────────────────
    private void mostrarDialogo(Route routeEditar) {
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_ruta, null);

        EditText etNombre      = dialogView.findViewById(R.id.etRouteName);
        EditText etDescripcion = dialogView.findViewById(R.id.etRouteDescription);
        EditText etHoraInicio  = dialogView.findViewById(R.id.etRouteTimeStart);
        EditText etHoraFin     = dialogView.findViewById(R.id.etRouteTimeEnd);

        boolean esEdicion = routeEditar != null;

        // Si es edición, rellenamos los campos
        if (esEdicion) {
            etNombre.setText(routeEditar.getNombre());
            etDescripcion.setText(routeEditar.getDescripcion());
            etHoraInicio.setText(routeEditar.getHoraInicio());
            etHoraFin.setText(routeEditar.getHoraFin());
        }

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(esEdicion ? "Editar ruta" : "Nueva ruta")
                .setView(dialogView)
                .create();

        // Botones del layout del diálogo
        dialogView.findViewById(R.id.btnCancel).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btnSave).setOnClickListener(v -> {
            String nombre      = etNombre.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();
            String horaInicio  = etHoraInicio.getText().toString().trim();
            String horaFin     = etHoraFin.getText().toString().trim();

            if (nombre.isEmpty()) {
                etNombre.setError("El nombre es obligatorio");
                return;
            }

            if (esEdicion) {
                actualizarRuta(routeEditar.getId(), nombre, descripcion, horaInicio, horaFin);
            } else {
                crearRuta(nombre, descripcion, horaInicio, horaFin);
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    // ─── CREAR ruta en Firestore ──────────────────────────────────────────────
    private void crearRuta(String nombre, String descripcion,
                           String horaInicio, String horaFin) {
        Map<String, Object> data = new HashMap<>();
        data.put("nombre", nombre);
        data.put("descripcion", descripcion);
        data.put("horaInicio", horaInicio);
        data.put("horaFin", horaFin);
        data.put("numParadas", 0);
        data.put("activa", false);

        db.collection(COL_ROUTES).add(data)
                .addOnSuccessListener(ref -> {
                    Toast.makeText(this, "Ruta creada correctamente", Toast.LENGTH_SHORT).show();
                    cargarRutas();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    // ─── ACTUALIZAR ruta en Firestore ─────────────────────────────────────────
    private void actualizarRuta(String id, String nombre, String descripcion,
                                String horaInicio, String horaFin) {
        Map<String, Object> data = new HashMap<>();
        data.put("nombre", nombre);
        data.put("descripcion", descripcion);
        data.put("horaInicio", horaInicio);
        data.put("horaFin", horaFin);

        db.collection(COL_ROUTES).document(id).update(data)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Ruta actualizada", Toast.LENGTH_SHORT).show();
                    cargarRutas();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    // ─── ELIMINAR ruta de Firestore ───────────────────────────────────────────
    private void eliminarRuta(Route route) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar ruta")
                .setMessage("¿Seguro que quieres eliminar \"" + route.getNombre() + "\"?")
                .setPositiveButton("Eliminar", (dialog, which) ->
                        db.collection(COL_ROUTES).document(route.getId()).delete()
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this, "Ruta eliminada", Toast.LENGTH_SHORT).show();
                                    cargarRutas();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                )
                )
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // ─── Estado vacío ─────────────────────────────────────────────────────────
    private void mostrarEstado() {
        if (routes.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            recyclerRoutes.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            recyclerRoutes.setVisibility(View.VISIBLE);
        }
    }

    // ─── Callbacks del adapter ────────────────────────────────────────────────
    @Override
    public void onEdit(Route route) {
        mostrarDialogo(route);
    }

    @Override
    public void onDelete(Route route) {
        eliminarRuta(route);
    }
}