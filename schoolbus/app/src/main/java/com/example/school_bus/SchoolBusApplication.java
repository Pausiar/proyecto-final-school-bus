package com.example.school_bus;

import android.app.Application;

import com.example.school_bus.firebase.FirebaseConfig;
import com.google.firebase.database.FirebaseDatabase;

public class SchoolBusApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (FirebaseConfig.ensureInitialized(this)) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
    }
}