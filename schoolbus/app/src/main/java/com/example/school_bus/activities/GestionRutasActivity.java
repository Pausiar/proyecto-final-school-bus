package com.example.school_bus.activities;

import androidx.appcompat.app.AlertDialog;
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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        db = FirebaseFirestore.getInstance();

        recyclerRoutes = findViewById(R.id.recyclerRoutes);
        layoutEmpty    = findViewById(R.id.layoutEmpty);
        FloatingActionButton fabAdd = findViewById(R.id.fabAddRoute);

        routes = new ArrayList<>();
        adapter = new RouteAdapter(routes, this);
        recyclerRoutes.setLayoutManager(new LinearLayoutManager(this));
        recyclerRoutes.setAdapter(adapter);

        fabAdd.setOnClickListener(v -> showDialog(null));

        loadRoutes();
    }

    private void loadRoutes() {
        db.collection(COL_ROUTES)
                .get()
                .addOnSuccessListener(query -> {
                    routes.clear();
                    for (QueryDocumentSnapshot doc : query) {
                        Route r = new Route();
                        r.setId(doc.getId());
                        r.setName(doc.getString("name"));
                        r.setDescription(doc.getString("description"));
                        r.setStartTime(doc.getString("startTime"));
                        r.setEndTime(doc.getString("endTime"));
                        Long stops = doc.getLong("stopCount");
                        r.setStopCount(stops != null ? stops.intValue() : 0);
                        Boolean active = doc.getBoolean("active");
                        r.setActive(active != null && active);
                        routes.add(r);
                    }
                    adapter.updateList(routes);
                    showEmptyState();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error loading routes: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show()
                );
    }

    private void showDialog(Route routeToEdit) {
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_ruta, null);

        EditText etName        = dialogView.findViewById(R.id.etRouteName);
        EditText etDescription = dialogView.findViewById(R.id.etRouteDescription);
        EditText etStartTime   = dialogView.findViewById(R.id.etRouteTimeStart);
        EditText etEndTime     = dialogView.findViewById(R.id.etRouteTimeEnd);

        boolean isEditing = routeToEdit != null;

        if (isEditing) {
            etName.setText(routeToEdit.getName());
            etDescription.setText(routeToEdit.getDescription());
            etStartTime.setText(routeToEdit.getStartTime());
            etEndTime.setText(routeToEdit.getEndTime());
        }

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(isEditing ? "Edit route" : "New route")
                .setView(dialogView)
                .create();

        dialogView.findViewById(R.id.btnCancel).setOnClickListener(v -> dialog.dismiss());
        dialogView.findViewById(R.id.btnSave).setOnClickListener(v -> {
            String name        = etName.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            String startTime   = etStartTime.getText().toString().trim();
            String endTime     = etEndTime.getText().toString().trim();

            if (name.isEmpty()) {
                etName.setError("Name is required");
                return;
            }

            if (isEditing) {
                updateRoute(routeToEdit.getId(), name, description, startTime, endTime);
            } else {
                createRoute(name, description, startTime, endTime);
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    private void createRoute(String name, String description,
                             String startTime, String endTime) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("description", description);
        data.put("startTime", startTime);
        data.put("endTime", endTime);
        data.put("stopCount", 0);
        data.put("active", false);

        db.collection(COL_ROUTES).add(data)
                .addOnSuccessListener(ref -> {
                    Toast.makeText(this, "Route created successfully", Toast.LENGTH_SHORT).show();
                    loadRoutes();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void updateRoute(String id, String name, String description,
                             String startTime, String endTime) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", name);
        data.put("description", description);
        data.put("startTime", startTime);
        data.put("endTime", endTime);

        db.collection(COL_ROUTES).document(id).update(data)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this, "Route updated", Toast.LENGTH_SHORT).show();
                    loadRoutes();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    private void deleteRoute(Route route) {
        new AlertDialog.Builder(this)
                .setTitle("Delete route")
                .setMessage("Are you sure you want to delete \"" + route.getName() + "\"?")
                .setPositiveButton("Delete", (dialog, which) ->
                        db.collection(COL_ROUTES).document(route.getId()).delete()
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this, "Route deleted", Toast.LENGTH_SHORT).show();
                                    loadRoutes();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                                )
                )
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showEmptyState() {
        if (routes.isEmpty()) {
            layoutEmpty.setVisibility(View.VISIBLE);
            recyclerRoutes.setVisibility(View.GONE);
        } else {
            layoutEmpty.setVisibility(View.GONE);
            recyclerRoutes.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onEdit(Route route) {
        showDialog(route);
    }

    @Override
    public void onDelete(Route route) {
        deleteRoute(route);
    }
}