package com.example.school_bus.models;

public class StudentStop {

    private String id;
    private String studentId;
    private String stopId;
    private String type; // boarding, dropoff, both
    private boolean active;

    public StudentStop() {}

    public StudentStop(String studentId, String stopId, String type) {
        this.studentId = studentId;
        this.stopId = stopId;
        this.type = type;
        this.active = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getStopId() { return stopId; }
    public void setStopId(String stopId) { this.stopId = stopId; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}