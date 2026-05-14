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
        TextView tvNombre, tvDescripcion, tvParadas, tvHorario, tvActiva;
        ImageButton btnEditar, btnEliminar;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvRouteName);
            tvDescripcion = itemView.findViewById(R.id.tvRouteDescription);
            tvParadas = itemView.findViewById(R.id.tvRouteStops);
            tvHorario = itemView.findViewById(R.id.tvRouteTime);
            tvActiva = itemView.findViewById(R.id.tvRouteActive);
            btnEditar = itemView.findViewById(R.id.btnEditRoute);
            btnEliminar = itemView.findViewById(R.id.btnDeleteRoute);
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

        holder.tvNombre.setText(route.getNombre());
        holder.tvDescripcion.setText(route.getDescripcion());
        holder.tvParadas.setText(route.getNumParadas() + " paradas");
        holder.tvHorario.setText(route.getHoraInicio() + " - " + route.getHoraFin());
        holder.tvActiva.setText(route.isActiva() ? "Activa" : "Inactiva");
        holder.tvActiva.setBackgroundColor(
                route.isActiva() ? 0xFF4CAF50 : 0xFFAAAAAA
        );

        holder.btnEditar.setOnClickListener(v -> listener.onEdit(route));
        holder.btnEliminar.setOnClickListener(v -> listener.onDelete(route));
    }

    public int getItemCount() {
        return routes.size();
    }

    public void updateList(List<Route> newRoutes) {
        this.routes = newRoutes;
        notifyDataSetChanged();
    }
}
