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
        TextView name, stop;

        public ViewHolder(View itemView) {
            super(itemView);
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
        holder.name.setText(s.getName());
        holder.stop.setText(s.getStop());
    }

    @Override
    public int getItemCount() {
        return students.size();
    }
}
