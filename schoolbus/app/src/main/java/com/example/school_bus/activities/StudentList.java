package com.example.school_bus.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.school_bus.R;
import com.example.school_bus.adapters.StudentAdapter;
import com.example.school_bus.database.FirebaseHelper;
import com.example.school_bus.models.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentList extends AppCompatActivity {

    private static final String TAG = "DEBUG_STUDENTS";

    RecyclerView recyclerView;
    StudentAdapter adapter;
    List<Student> students;
    FirebaseHelper firebaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recyclerStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseHelper = new FirebaseHelper();
        students = new ArrayList<>();

        adapter = new StudentAdapter(students);
        recyclerView.setAdapter(adapter);

        loadStudents();
    }

    private void loadStudents() {
        Log.d(TAG, "Starting to load students...");
        firebaseHelper.getAllStudents(new FirebaseHelper.OnListListener() {
            @Override
            public void onSuccess(List<Map<String, Object>> list) {
                Log.d(TAG, "Documents received: " + list.size());
                students.clear();
                for (Map<String, Object> data : list) {
                    Log.d(TAG, "Student data: " + data.toString());
                    Student s = new Student(
                            (String) data.get("user_id"),
                            (String) data.get("name"),
                            null,
                            null,
                            (String) data.get("pickupAddress"),
                            null
                    );
                    s.setId((String) data.get("id"));
                    students.add(s);
                }
                adapter.notifyDataSetChanged();

                if (students.isEmpty()) {
                    Log.d(TAG, "List is empty after processing");
                    Toast.makeText(StudentList.this, "No students registered",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(String error) {
                Log.e(TAG, "Error loading students: " + error);
                Toast.makeText(StudentList.this, "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}