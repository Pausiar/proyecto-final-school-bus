package com.example.school_bus.models;

public class Route {

    private String id;
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private int stopCount;
    private boolean active;

    public Route() {}

    public Route(String name, String description, String startTime, String endTime) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.stopCount = 0;
        this.active = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }

    public int getStopCount() { return stopCount; }
    public void setStopCount(int stopCount) { this.stopCount = stopCount; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}