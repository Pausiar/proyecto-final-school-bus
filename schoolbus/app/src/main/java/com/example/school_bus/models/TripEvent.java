package com.example.school_bus.models;

public class TripEvent {

    private String id;
    private String idViaje;
    private String tipoEvento; // inicio, parada, incidente, fin
    private String fechaHora;
    private double latitud;
    private double longitud;
    private String descripcion;

    public TripEvent() {}

    public TripEvent(String idViaje, String tipoEvento,
                     String fechaHora, double latitud, double longitud) {
        this.idViaje = idViaje;
        this.tipoEvento = tipoEvento;
        this.fechaHora = fechaHora;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdViaje() { return idViaje; }
    public void setIdViaje(String idViaje) { this.idViaje = idViaje; }

    public String getTipoEvento() { return tipoEvento; }
    public void setTipoEvento(String tipoEvento) { this.tipoEvento = tipoEvento; }

    public String getFechaHora() { return fechaHora; }
    public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}