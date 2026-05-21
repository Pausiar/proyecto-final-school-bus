package com.example.school_bus.models;

public class Trip {

    private String id;
    private String routeId;
    private String driverId;
    private String vehicleId;
    private String tripDate;
    private String actualStartTime;
    private String actualEndTime;
    private String status; // scheduled, in_progress, completed, cancelled
    private String notes;

    public Trip() {}

    public Trip(String routeId, String driverId, String vehicleId,
                String tripDate, String status) {
        this.routeId = routeId;
        this.driverId = driverId;
        this.vehicleId = vehicleId;
        this.tripDate = tripDate;
        this.status = status;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRouteId() { return routeId; }
    public void setRouteId(String routeId) { this.routeId = routeId; }

    public String getDriverId() { return driverId; }
    public void setDriverId(String driverId) { this.driverId = driverId; }

    public String getVehicleId() { return vehicleId; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }

    public String getTripDate() { return tripDate; }
    public void setTripDate(String tripDate) { this.tripDate = tripDate; }

    public String getActualStartTime() { return actualStartTime; }
    public void setActualStartTime(String actualStartTime) { this.actualStartTime = actualStartTime; }

    public String getActualEndTime() { return actualEndTime; }
    public void setActualEndTime(String actualEndTime) { this.actualEndTime = actualEndTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}