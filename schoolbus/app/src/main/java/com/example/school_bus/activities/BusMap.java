package com.example.school_bus.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.school_bus.R;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

public class BusMap extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_map);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;

        LatLng school = new LatLng(40.4168, -3.7038);
        googleMap.addMarker(
                new MarkerOptions().position(school).title("Escuela")
        );

        googleMap.moveCamera(
                CameraUpdateFactory.newLatLngZoom(school, 14)
        );
    }
}
