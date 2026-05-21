package com.example.school_bus.models;

public class StudentRoute {

    private String id;
    private String studentId;
    private String routeId;
    private String boardingStopId;
    private String dropoffStopId;
    private String assignmentDate;
    private boolean active;

    public StudentRoute() {}

    public StudentRoute(String studentId, String routeId,
                        String boardingStopId, String dropoffStopId,
                        String assignmentDate) {
        this.studentId = studentId;
        this.routeId = routeId;
        this.boardingStopId = boardingStopId;
        this.dropoffStopId = dropoffStopId;
        this.assignmentDate = assignmentDate;
        this.active = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getRouteId() { return routeId; }
    public void setRouteId(String routeId) { this.routeId = routeId; }

    public String getBoardingStopId() { return boardingStopId; }
    public void setBoardingStopId(String boardingStopId) { this.boardingStopId = boardingStopId; }

    public String getDropoffStopId() { return dropoffStopId; }
    public void setDropoffStopId(String dropoffStopId) { this.dropoffStopId = dropoffStopId; }

    public String getAssignmentDate() { return assignmentDate; }
    public void setAssignmentDate(String assignmentDate) { this.assignmentDate = assignmentDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}