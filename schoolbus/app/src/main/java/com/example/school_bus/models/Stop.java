package com.example.school_bus.models;

import java.io.Serializable;

public class Stop implements Serializable {

    private String id;
    private String routeId;
    private String stopName;
    private String address;
    private double latitude;
    private double longitude;
    private int stopOrder;
    private String estimatedTime;
    private String estimatedArrival;
    private String reference;
    private String status;
    private boolean active;
    private double radius;
    private long createdAt;
    private long updatedAt;
    private String notes;

    public Stop() {}

    public Stop(String routeId, String stopName, String address,
                double latitude, double longitude, int stopOrder) {
        this.routeId = routeId;
        this.stopName = stopName;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.stopOrder = stopOrder;
        this.status = "pending";
        this.active = true;
        this.radius = 50.0;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
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

    public String getEstimatedArrival() { return estimatedArrival; }
    public void setEstimatedArrival(String estimatedArrival) { this.estimatedArrival = estimatedArrival; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public double getRadius() { return radius; }
    public void setRadius(double radius) { this.radius = radius; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public double getDistanceTo(double latitude, double longitude) {
        final int EARTH_RADIUS = 6371000;
        double dLat = Math.toRadians(latitude - this.latitude);
        double dLon = Math.toRadians(longitude - this.longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(latitude)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    public boolean isWithinRadius(double latitude, double longitude) {
        return getDistanceTo(latitude, longitude) <= this.radius;
    }

    public void markAsVisited() {
        this.status = "visited";
        this.updatedAt = System.currentTimeMillis();
    }

    public void markAsPending() {
        this.status = "pending";
        this.updatedAt = System.currentTimeMillis();
    }

    public void markAsInProgress() {
        this.status = "in_progress";
        this.updatedAt = System.currentTimeMillis();
    }

    public String getFullDescription() {
        return String.format("%s - %s (Orden: %d)", stopName, address, stopOrder);
    }

    public String getCoordinatesString() {
        return String.format("%.6f, %.6f", latitude, longitude);
    }

    public boolean isStopActive() {
        return active && "pending".equals(status) || "in_progress".equals(status);
    }

    @Override
    public String toString() {
        return "Stop{" +
                "id='" + id + '\'' +
                ", stopName='" + stopName + '\'' +
                ", address='" + address + '\'' +
                ", stopOrder=" + stopOrder +
                ", status='" + status + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}