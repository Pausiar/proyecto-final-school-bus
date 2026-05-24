package com.example.school_bus.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.school_bus.R;
import com.example.school_bus.session.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
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

    private static final int PERMISSION_REQUEST_CODE = 1001;

    private MapView mapView;
    private Marker busMarker;
    private FirebaseFirestore db;
    private LocationManager locationManager;
    private ListenerRegistration firestoreListener;
    private String driverUid;
    private String userRole;

    // ✅ Tu activity escucha el GPS directamente, sin tocar el servicio del compañero
    private final LocationListener gpsListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            // Publica en Firestore
            publicarUbicacion(lat, lng);

            // Actualiza el mapa localmente también
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

        db = FirebaseFirestore.getInstance();
        driverUid = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;
        userRole = SessionManager.getRole(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(15.0);

        // gestionar permisos e iniciar ubicacion
        if (com.example.school_bus.utils.PermisosUtils.tienePermisosUbicacion(this)) {
            iniciarServicioUbicacion();
        } else {
            com.example.school_bus.utils.PermisosUtils.solicitarPermisosUbicacion(this, 100);
        }
    }

    private void iniciarFuncionalidad() {
        if ("driver".equalsIgnoreCase(userRole)) {
            // El conductor publica su posición GPS en Firestore
            activarGPS();
        } else {
            // Padre/estudiante solo ve el mapa en tiempo real desde Firestore
            if (driverUid != null) escucharUbicacion(driverUid);
        }
    }

    private void activarGPS() {
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,  // cada 5 segundos
                    5f,    // o cada 5 metros
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
    private void iniciarServicioUbicacion() {
        String role = SessionManager.getRole(this);
        if ("driver".equalsIgnoreCase(role)) {
            startService(new Intent(this, UbicacionService.class));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (com.example.school_bus.utils.PermisosUtils.tienePermisosUbicacion(this)) {
                iniciarServicioUbicacion();
            }
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
        // Parar GPS al salir de la pantalla para ahorrar batería
        if (locationManager != null) locationManager.removeUpdates(gpsListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (firestoreListener != null) firestoreListener.remove();
    }
}