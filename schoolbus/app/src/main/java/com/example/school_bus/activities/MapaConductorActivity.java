package com.example.school_bus.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.example.school_bus.services.UbicacionService;
import com.example.school_bus.session.SessionManager;
import com.example.school_bus.utils.PermisosUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.HashMap;
import java.util.Map;

public class MapaConductorActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;

    private MapView mapView;
    private Marker busMarker;
    private FirebaseFirestore db;
    private LocationManager locationManager;
    private ListenerRegistration firestoreListener;
    private String driverUid;
    private String userRole;

    private final LocationListener gpsListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            publicarUbicacion(lat, lng);
            actualizarMarcador(lat, lng);
        }
        @Override public void onProviderEnabled(String provider) {}
        @Override public void onProviderDisabled(String provider) {}
        @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(),
                getSharedPreferences("osm", MODE_PRIVATE));
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_bus_map);

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        db = FirebaseFirestore.getInstance();

        // Guard: si Firebase Auth aún no tiene sesión, cerrar con mensaje claro
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "Sesión no iniciada. Vuelve a hacer login.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        driverUid = currentUser.getUid();

        userRole = SessionManager.getRole(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(15.0);

        // Pedir permisos o iniciar directamente
        if (PermisosUtils.tienePermisosUbicacion(this)) {
            iniciarFuncionalidad();
        } else {
            PermisosUtils.solicitarPermisosUbicacion(this, PERMISSION_REQUEST_CODE);
        }
    }

    private void iniciarFuncionalidad() {
        if ("driver".equalsIgnoreCase(userRole)) {
            startService(new Intent(this, UbicacionService.class));
            activarGPS();
        } else {
            escucharUbicacion(driverUid);
        }
    }

    private void activarGPS() {
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    5f,
                    gpsListener
            );
        } catch (SecurityException e) {
            Toast.makeText(this, "Error al activar GPS", Toast.LENGTH_SHORT).show();
        }
    }

    private void publicarUbicacion(double lat, double lng) {
        if (driverUid == null) return;
        Map<String, Object> data = new HashMap<>();
        data.put("lat", lat);
        data.put("lng", lng);
        data.put("timestamp", System.currentTimeMillis());
        data.put("driverUid", driverUid);
        db.collection("buses").document(driverUid).set(data);
    }

    private void escucharUbicacion(String uid) {
        firestoreListener = db.collection("buses").document(uid)
                .addSnapshotListener((snapshot, error) -> {
                    if (error != null || snapshot == null || !snapshot.exists()) return;
                    Double lat = snapshot.getDouble("lat");
                    Double lng = snapshot.getDouble("lng");
                    if (lat == null || lng == null) return;
                    actualizarMarcador(lat, lng);
                });
    }

    private void actualizarMarcador(double lat, double lng) {
        GeoPoint punto = new GeoPoint(lat, lng);
        if (busMarker == null) {
            busMarker = new Marker(mapView);
            busMarker.setTitle("Bus escolar");
            mapView.getOverlays().add(busMarker);
        }
        busMarker.setPosition(punto);
        mapView.getController().setCenter(punto);
        mapView.invalidate();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                iniciarFuncionalidad();
            } else {
                Toast.makeText(this,
                        "Permiso de ubicación necesario", Toast.LENGTH_LONG).show();
                finish();
            }
        }
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
        if (locationManager != null) locationManager.removeUpdates(gpsListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (firestoreListener != null) firestoreListener.remove();
    }
}