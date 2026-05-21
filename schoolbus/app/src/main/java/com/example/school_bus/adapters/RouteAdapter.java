package com.example.school_bus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.school_bus.R;
import com.example.school_bus.models.Route;

import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {

    private List<Route> routes;
    private OnRouteListener listener;

    public interface OnRouteListener {
        void onEdit(Route route);

        void onDelete(Route route);
    }

    public RouteAdapter(List<Route> routes, OnRouteListener listener) {
        this.routes = routes;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDescription, tvStops, tvTime, tvActive;
        ImageButton btnEdit, btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvRouteName);
            tvDescription = itemView.findViewById(R.id.tvRouteDescription);
            tvStops = itemView.findViewById(R.id.tvRouteStops);
            tvTime = itemView.findViewById(R.id.tvRouteTime);
            tvActive = itemView.findViewById(R.id.tvRouteActive);
            btnEdit = itemView.findViewById(R.id.btnEditRoute);
            btnDelete = itemView.findViewById(R.id.btnDeleteRoute);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Route route = routes.get(position);

        holder.tvName.setText(route.getName());
        holder.tvDescription.setText(route.getDescription());
        holder.tvStops.setText(route.getStopCount() + " stops");
        holder.tvTime.setText(route.getStartTime() + " - " + route.getEndTime());
        holder.tvActive.setText(route.isActive() ? "Active" : "Inactive");
        holder.tvActive.setBackgroundColor(
                route.isActive() ? 0xFF4CAF50 : 0xFFAAAAAA
        );

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(route));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(route));
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public void updateList(List<Route> newRoutes) {
        this.routes = newRoutes;
        notifyDataSetChanged();
    }
}