package com.example.school_bus.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school_bus.R;
import com.example.school_bus.adapters.NotificationAdapter;
import com.example.school_bus.database.FirebaseHelper;
import com.example.school_bus.models.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NotificationAdapter adapter;
    List<Notification> notifications;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        recyclerView = findViewById(R.id.recyclerNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseHelper = new FirebaseHelper();
        notifications = new ArrayList<>();

        adapter = new NotificationAdapter(notifications);
        recyclerView.setAdapter(adapter);

        loadNotifications();
    }

    private void loadNotifications() {
        firebaseHelper.getAllNotifications(new FirebaseHelper.OnListListener() {
            @Override
            public void onSuccess(List<Map<String, Object>> list) {
                notifications.clear();
                for (Map<String, Object> data : list) {
                    Notification n = new Notification(
                            (String) data.get("title"),
                            (String) data.get("message"),
                            (String) data.get("date")
                    );
                    n.setId((String) data.get("id"));
                    notifications.add(n);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(String error) {
                // mostrar error si es necesario
            }
        });
    }
}