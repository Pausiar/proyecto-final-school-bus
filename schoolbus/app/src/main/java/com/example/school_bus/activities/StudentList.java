package com.example.school_bus.activities;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school_bus.R;
import com.example.school_bus.adapters.StudentAdapter;
import com.example.school_bus.database.DBHelper;
import com.example.school_bus.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentList extends AppCompatActivity {

    RecyclerView recyclerView;
    StudentAdapter adapter;
    List<Student> students;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        recyclerView = findViewById(R.id.recyclerStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new DBHelper(this);
        students = new ArrayList<>();

        loadStudents();

        adapter = new StudentAdapter(students);
        recyclerView.setAdapter(adapter);
    }

    private void loadStudents() {
        Cursor cursor = dbHelper.getAllStudents();

        while (cursor.moveToNext()) {
            students.add(new Student(
                    cursor.getInt(0),      // id
                    cursor.getString(1),   // name
                    cursor.getString(2)    // stop
            ));
        }

        cursor.close();
    }
}
