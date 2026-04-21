package com.example.school_bus.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class BusMap extends AppCompatActivity {

    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration.getInstance().load(getApplicationContext(), getSharedPreferences("osm", MODE_PRIVATE));
        Configuration.getInstance().setUserAgentValue(getPackageName());
        setContentView(R.layout.activity_bus_map);

        mapView = findViewById(R.id.map);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);

        GeoPoint school = new GeoPoint(40.4168, -3.7038);
        mapView.getController().setZoom(14.0);
        mapView.getController().setCenter(school);

        Marker schoolMarker = new Marker(mapView);
        schoolMarker.setPosition(school);
        schoolMarker.setTitle("Escuela");
        mapView.getOverlays().add(schoolMarker);
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
}
