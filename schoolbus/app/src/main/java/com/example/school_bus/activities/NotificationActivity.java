package com.example.school_bus.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
    TextView tvEmptyNotifications;
    NotificationAdapter adapter;
    List<Notification> notifications;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerNotifications);
        tvEmptyNotifications = findViewById(R.id.tvEmptyNotifications);
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
                List<Notification> loadedNotifications = new ArrayList<>();
                for (Map<String, Object> data : list) {
                    Notification n = new Notification(
                            (String) data.get("title"),
                            (String) data.get("message"),
                            (String) data.get("date")
                    );
                    n.setId((String) data.get("id"));
                    loadedNotifications.add(n);
                }
                adapter.replaceItems(loadedNotifications);
                updateEmptyState();
            }

            @Override
            public void onFailure(String error) {
                android.widget.Toast.makeText(NotificationActivity.this,
                        "Error al cargar notificaciones", android.widget.Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateEmptyState() {
        boolean isEmpty = notifications.isEmpty();
        tvEmptyNotifications.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        recyclerView.setVisibility(isEmpty ? View.GONE : View.VISIBLE);
    }
}