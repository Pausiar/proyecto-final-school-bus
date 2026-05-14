package com.example.school_bus.models;

public class Route {

    private String id;       // Firebase usa String, no int
    private String nombre;
    private String descripcion;
    private String horaInicio;
    private String horaFin;
    private int numParadas;
    private boolean activa;

    public Route() {}

    public Route(String nombre, String descripcion, String horaInicio, String horaFin) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.numParadas = 0;
        this.activa = false;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public int getNumParadas() { return numParadas; }
    public void setNumParadas(int numParadas) { this.numParadas = numParadas; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
}