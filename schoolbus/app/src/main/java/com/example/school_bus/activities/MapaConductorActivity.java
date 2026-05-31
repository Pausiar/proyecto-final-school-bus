package com.example.school_bus.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.school_bus.R;
import com.example.school_bus.services.UbicacionService;
import com.example.school_bus.session.SessionManager;
import com.example.school_bus.utils.PermisosUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapaConductorActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String PREFS_ROUTE = "route";
    private static final String KEY_ROUTE_ACTIVE = "active";

    private MapView mapView;
    private Marker busMarker;
    private TextView tvServiceStatus;
    private TextView tvRouteInfo;
    private FloatingActionButton fabStartRoute;
    private FirebaseFirestore db;
    private ListenerRegistration locationListener;
    private String driverUid;
    private boolean routeActive;
    private boolean startRoutePending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("osm", MODE_PRIVATE));
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_bus_map);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        db = FirebaseFirestore.getInstance();
        driverUid = resolveDriverUid();

        mapView = findViewById(R.id.map);
        tvServiceStatus = findViewById(R.id.tvServiceStatus);
        tvRouteInfo = findViewById(R.id.tvRouteInfo);
        fabStartRoute = findViewById(R.id.fabStartRoute);
        FloatingActionButton fabLocation = findViewById(R.id.fabLocation);

        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(new GeoPoint(41.3851, 2.1734));

        boolean isDriver = isDriver();
        routeActive = getSharedPreferences(PREFS_ROUTE, MODE_PRIVATE).getBoolean(KEY_ROUTE_ACTIVE, false);
        fabStartRoute.setVisibility(isDriver ? View.VISIBLE : View.GONE);
        fabStartRoute.setOnClickListener(v -> toggleRoute());
        fabLocation.setOnClickListener(v -> centerOnBus());
        updateRouteUi();

        if (driverUid != null) {
            listenBusLocation(driverUid);
        } else {
            tvRouteInfo.setText(R.string.bus_link_required);
        }

        if (isDriver && routeActive && PermisosUtils.tienePermisosUbicacion(this)) {
            startLocationService();
        }
    }

    private String resolveDriverUid() {
        if (isDriver()) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                return FirebaseAuth.getInstance().getCurrentUser().getUid();
            }
            return emptyToNull(SessionManager.getUid(this));
        }
        return emptyToNull(SessionManager.getLinkedDriverUid(this));
    }

    private boolean isDriver() {
        return "driver".equalsIgnoreCase(SessionManager.getRole(this));
    }

    private String emptyToNull(String value) {
        return value == null || value.trim().isEmpty() ? null : value;
    }

    private void toggleRoute() {
        if (routeActive) {
            stopService(new Intent(this, UbicacionService.class));
            setRouteActive(false);
            return;
        }

        if (!PermisosUtils.tienePermisosUbicacion(this)) {
            startRoutePending = true;
            PermisosUtils.solicitarPermisosUbicacion(this, PERMISSION_REQUEST_CODE);
            return;
        }

        startLocationService();
        setRouteActive(true);
    }

    private void startLocationService() {
        Intent intent = new Intent(this, UbicacionService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
        } else {
            startService(intent);
        }
    }

    private void setRouteActive(boolean active) {
        routeActive = active;
        getSharedPreferences(PREFS_ROUTE, MODE_PRIVATE)
                .edit()
                .putBoolean(KEY_ROUTE_ACTIVE, active)
                .apply();
        updateRouteUi();
    }

    private void updateRouteUi() {
        if (!isDriver()) {
            tvServiceStatus.setText(R.string.bus_tracking_title);
            tvRouteInfo.setText(R.string.bus_waiting_location);
            return;
        }
        tvServiceStatus.setText(routeActive ? R.string.bus_service_active : R.string.bus_service_inactive);
        tvRouteInfo.setText(routeActive ? R.string.bus_route_tracking : R.string.bus_route_inactive);
        fabStartRoute.setImageResource(routeActive ? android.R.drawable.ic_media_pause : android.R.drawable.ic_media_play);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (PermisosUtils.tienePermisosUbicacion(this)) {
                if (startRoutePending) {
                    startLocationService();
                    setRouteActive(true);
                }
            } else {
                Toast.makeText(this, "permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
            }
            startRoutePending = false;
        }
    }

    private void listenBusLocation(String uid) {
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
            busMarker.setTitle(getString(R.string.bus_marker_title));
            mapView.getOverlays().add(busMarker);
        }

        busMarker.setPosition(point);
        mapView.getController().setCenter(point);
        mapView.invalidate();
    }

    private void centerOnBus() {
        if (busMarker == null) {
            Toast.makeText(this, "ubicación del bus no disponible", Toast.LENGTH_SHORT).show();
            return;
        }
        mapView.getController().animateTo(busMarker.getPosition());
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
        if (locationListener != null) {
            locationListener.remove();
        }
    }
}