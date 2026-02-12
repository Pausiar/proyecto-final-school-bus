package com.example.school_bus.models;

//para route paradas

public class Route {

    private int id;
    private int busId;
    private int stopId;
    private int orden;

    public Route() {}

    public Route(int busId, int stopId, int orden) {
        this.busId = busId;
        this.stopId = stopId;
        this.orden = orden;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getBusId() { return busId; }
    public void setBusId(int busId) { this.busId = busId; }

    public int getStopId() { return stopId; }
    public void setStopId(int stopId) { this.stopId = stopId; }

    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }
}
