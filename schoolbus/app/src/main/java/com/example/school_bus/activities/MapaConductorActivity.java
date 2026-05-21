package com.example.school_bus.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.Arrays;

public class MapaConductorActivity extends AppCompatActivity {

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

        GeoPoint start  = new GeoPoint(40.4168, -3.7038);
        GeoPoint stop   = new GeoPoint(40.4194, -3.7001);
        GeoPoint school = new GeoPoint(40.4217, -3.6970);

        mapView.getController().setZoom(14.5);
        mapView.getController().setCenter(stop);

        addMarker(start,  "inicio ruta");
        addMarker(stop,   "parada activa");
        addMarker(school, "escuela");

        Polyline route = new Polyline();
        route.setPoints(Arrays.asList(start, stop, school));
        mapView.getOverlays().add(route);
    }

    private void addMarker(GeoPoint point, String title) {
        Marker marker = new Marker(mapView);
        marker.setPosition(point);
        marker.setTitle(title);
        mapView.getOverlays().add(marker);
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