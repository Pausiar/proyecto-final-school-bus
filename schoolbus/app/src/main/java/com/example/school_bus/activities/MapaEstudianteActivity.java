package com.example.school_bus.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.example.school_bus.session.SessionManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MapaEstudianteActivity extends AppCompatActivity {

    private MapView mapView;
    private Marker busMarker;
    private DatabaseReference studentsRef;
    private DatabaseReference busesRef;
    private ValueEventListener studentListener;
    private ValueEventListener busLocationListener;
    private String studentUid;
    private String assignedBusUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(),
                getSharedPreferences("osm", MODE_PRIVATE));
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_bus_map);

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        studentsRef = db.getReference("students");
        busesRef = db.getReference("buses");
        studentUid = FirebaseAuth.getInstance().getCurrentUser() != null
                ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                : null;

        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(new GeoPoint(40.4168, -3.7038));

        if (studentUid != null) {
            getAssignedBus();
        }
    }

    private void getAssignedBus() {
        studentListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.exists()) return;
                assignedBusUid = snapshot.child("busId").getValue(String.class);
                if (assignedBusUid != null && !assignedBusUid.isEmpty()) {
                    listenBusLocation(assignedBusUid);
                }
            }
            @Override public void onCancelled(DatabaseError error) {}
        };
        studentsRef.child(studentUid).addValueEventListener(studentListener);
    }

    private void listenBusLocation(String busUid) {
        busLocationListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.exists()) return;
                Double lat = snapshot.child("lat").getValue(Double.class);
                Double lng = snapshot.child("lng").getValue(Double.class);
                if (lat == null || lng == null) return;
                updateMapMarker(lat, lng);
            }
            @Override public void onCancelled(DatabaseError error) {}
        };
        busesRef.child(busUid).addValueEventListener(busLocationListener);
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
        if (studentListener != null && studentUid != null) {
            studentsRef.child(studentUid).removeEventListener(studentListener);
        }
        if (busLocationListener != null && assignedBusUid != null) {
            busesRef.child(assignedBusUid).removeEventListener(busLocationListener);
        }
    }
}

