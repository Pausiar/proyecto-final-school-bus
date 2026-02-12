package com.example.school_bus.models;

public class Bus {

    private int id;
    private String nombre;
    private String placa;
    private int conductorId;

    public Bus() {}

    public Bus(String nombre, String placa, int conductorId) {
        this.nombre = nombre;
        this.placa = placa;
        this.conductorId = conductorId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public int getConductorId() { return conductorId; }
    public void setConductorId(int conductorId) { this.conductorId = conductorId; }
}
