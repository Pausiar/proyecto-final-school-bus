package com.example.school_bus.models;

public class Stop {

    private String id;
    private String routeId;
    private String stopName;
    private String address;
    private double latitude;
    private double longitude;
    private int stopOrder;
    private String estimatedTime;
    private String reference;

    public Stop() {}

    public Stop(String routeId, String stopName, String address,
                double latitude, double longitude, int stopOrder) {
        this.routeId = routeId;
        this.stopName = stopName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stopOrder = stopOrder;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRouteId() { return routeId; }
    public void setRouteId(String routeId) { this.routeId = routeId; }

    public String getStopName() { return stopName; }
    public void setStopName(String stopName) { this.stopName = stopName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public int getStopOrder() { return stopOrder; }
    public void setStopOrder(int stopOrder) { this.stopOrder = stopOrder; }

    public String getEstimatedTime() { return estimatedTime; }
    public void setEstimatedTime(String estimatedTime) { this.estimatedTime = estimatedTime; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }
}