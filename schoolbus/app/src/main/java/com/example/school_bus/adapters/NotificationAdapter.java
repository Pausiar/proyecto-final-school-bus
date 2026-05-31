package com.example.school_bus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.school_bus.R;
import com.example.school_bus.models.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    List<Notification> notifications;

    public NotificationAdapter(List<Notification> notifications) {
        this.notifications = notifications;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, message;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            message = itemView.findViewById(R.id.tvMessage);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notification n = notifications.get(position);
        holder.title.setText(n.getTitle());
        holder.message.setText(n.getMessage());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void replaceItems(List<Notification> newNotifications) {
        int oldSize = notifications.size();
        if (oldSize > 0) {
            notifications.clear();
            notifyItemRangeRemoved(0, oldSize);
        }
        notifications.addAll(newNotifications);
        if (!notifications.isEmpty()) {
            notifyItemRangeInserted(0, notifications.size());
        }
    }
}
