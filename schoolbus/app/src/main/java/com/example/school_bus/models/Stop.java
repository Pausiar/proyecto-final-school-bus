package com.example.school_bus.models;

public class Stop {

    private int id;
    private String nombre;
    private double latitud;
    private double longitud;
    private int busId;

    public Stop() {}

    public Stop(String nombre, double latitud, double longitud, int busId) {
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.busId = busId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public int getBusId() { return busId; }
    public void setBusId(int busId) { this.busId = busId; }
}
