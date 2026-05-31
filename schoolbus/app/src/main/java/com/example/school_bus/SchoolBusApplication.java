package com.example.school_bus;

import android.app.Application;

import com.example.school_bus.firebase.FirebaseConfig;

public class SchoolBusApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseConfig.ensureInitialized(this);
    }
}