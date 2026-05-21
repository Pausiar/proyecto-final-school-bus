package com.example.school_bus.models;

public class TripEvent {

    private String id;
    private String tripId;
    private String eventType; // start, stop, incident, end
    private String dateTime;
    private double latitude;
    private double longitude;
    private String description;

    public TripEvent() {}

    public TripEvent(String tripId, String eventType,
                     String dateTime, double latitude, double longitude) {
        this.tripId = tripId;
        this.eventType = eventType;
        this.dateTime = dateTime;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTripId() { return tripId; }
    public void setTripId(String tripId) { this.tripId = tripId; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getDateTime() { return dateTime; }
    public void setDateTime(String dateTime) { this.dateTime = dateTime; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}