package com.example.school_bus.activities;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school_bus.R;
import com.example.school_bus.adapters.NotificationAdapter;
import com.example.school_bus.database.DBHelper;
import com.example.school_bus.models.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NotificationAdapter adapter;
    List<Notification> notifications;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recyclerNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);
        notifications = new ArrayList<>();

        loadNotifications(); // <-- convertimos Cursor a List

        adapter = new NotificationAdapter(notifications);
        recyclerView.setAdapter(adapter);
    }

    private void loadNotifications() {
        Cursor cursor = dbHelper.getAllNotifications();

        while (cursor.moveToNext()) {
            // id = columna 0, title = columna 1, message = columna 2
            notifications.add(new Notification(
                    cursor.getInt(0),      // id
                    cursor.getString(1),   // title
                    cursor.getString(2),   // message
                    ""                      // date vacío por ahora
            ));
        }

        cursor.close();
    }
}