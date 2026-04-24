package com.example.school_bus.models;

public class Stop {

    private String id;
    private String idRuta;
    private String nombreParada;
    private String direccion;
    private double latitud;
    private double longitud;
    private int ordenParada;
    private String horaEstimada;
    private String referencia;

    public Stop() {}

    public Stop(String idRuta, String nombreParada, String direccion,
                double latitud, double longitud, int ordenParada) {
        this.idRuta = idRuta;
        this.nombreParada = nombreParada;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.ordenParada = ordenParada;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdRuta() { return idRuta; }
    public void setIdRuta(String idRuta) { this.idRuta = idRuta; }

    public String getNombreParada() { return nombreParada; }
    public void setNombreParada(String nombreParada) { this.nombreParada = nombreParada; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public int getOrdenParada() { return ordenParada; }
    public void setOrdenParada(int ordenParada) { this.ordenParada = ordenParada; }

    public String getHoraEstimada() { return horaEstimada; }
    public void setHoraEstimada(String horaEstimada) { this.horaEstimada = horaEstimada; }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) { this.referencia = referencia; }
}