package com.example.school_bus.models;

public class Student {

    private int id;
    private String name;
    private String stop;

    public Student() {}

    public Student(int id, String name, String stop) {
        this.id = id;
        this.name = name;
        this.stop = stop;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getStop() { return stop; }
    public void setStop(String stop) { this.stop = stop; }
}
