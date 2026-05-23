package com.example.school_bus.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.example.school_bus.services.UbicacionService;
import com.example.school_bus.session.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapaConductorActivity extends AppCompatActivity {

    private MapView mapView;
    private Marker busMarker;
    private FirebaseFirestore db;
    private ListenerRegistration locationListener;
    private String driverUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(),
                getSharedPreferences("osm", MODE_PRIVATE));
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_bus_map);

        db = FirebaseFirestore.getInstance();
        driverUid = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;

        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(15.0);

        // Iniciar el servicio GPS si el usuario es conductor
        String role = SessionManager.getRole(this);
        if ("driver".equalsIgnoreCase(role)) {
            startService(new Intent(this, UbicacionService.class));
        }

        // Escuchar la posición en tiempo real desde Firestore
        if (driverUid != null) {
            listenBusLocation(driverUid);
        }
    }

    private void listenBusLocation(String uid) {
        // Escucha cambios en tiempo real en el documento del conductor
        locationListener = db.collection("buses").document(uid)
                .addSnapshotListener((snapshot, error) -> {
                    if (error != null || snapshot == null || !snapshot.exists()) return;

                    Double lat = snapshot.getDouble("lat");
                    Double lng = snapshot.getDouble("lng");
                    if (lat == null || lng == null) return;

                    updateMapMarker(lat, lng);
                });
    }

    private void updateMapMarker(double lat, double lng) {
        GeoPoint point = new GeoPoint(lat, lng);

        if (busMarker == null) {
            busMarker = new Marker(mapView);
            busMarker.setTitle("Bus escolar");
            mapView.getOverlays().add(busMarker);
        }

        busMarker.setPosition(point);
        mapView.getController().setCenter(point);
        mapView.invalidate(); // redibuja el mapa
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Dejar de escuchar Firestore al salir
        if (locationListener != null) {
            locationListener.remove();
        }
    }
}