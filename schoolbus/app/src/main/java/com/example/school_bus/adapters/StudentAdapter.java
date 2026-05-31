package com.example.school_bus.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.school_bus.R;
import com.example.school_bus.models.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    List<Student> students;

    public StudentAdapter(List<Student> students) {
        this.students = students;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView initial, name, stop;

        public ViewHolder(View itemView) {
            super(itemView);
            initial = itemView.findViewById(R.id.tvStudentInitial);
            name = itemView.findViewById(R.id.tvStudentName);
            stop = itemView.findViewById(R.id.tvStudentStop);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_student, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student s = students.get(position);
        String name = safeText(s.getName(), "Estudiante");
        holder.initial.setText(name.substring(0, 1).toUpperCase(java.util.Locale.ROOT));
        holder.name.setText(name);
        holder.stop.setText(safeText(s.getPickupAddress(), "Parada sin asignar"));
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public void replaceItems(List<Student> newStudents) {
        int oldSize = students.size();
        if (oldSize > 0) {
            students.clear();
            notifyItemRangeRemoved(0, oldSize);
        }
        students.addAll(newStudents);
        if (!students.isEmpty()) {
            notifyItemRangeInserted(0, students.size());
        }
    }

    private String safeText(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value;
    }
}