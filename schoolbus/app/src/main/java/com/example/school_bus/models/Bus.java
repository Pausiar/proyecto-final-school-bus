package com.example.school_bus.models;

public class Bus {

    private String id;
    private String numeroBus;
    private String placa;
    private String marca;
    private String modelo;
    private int anio;
    private int capacidadPasajeros;
    private String color;
    private String estado; // activo, mantenimiento, fuera_servicio
    private String ultimaRevision;
    private String proximaRevision;
    private String notas;

    public Bus() {}

    public Bus(String numeroBus, String placa, String marca,
               String modelo, int anio, int capacidadPasajeros) {
        this.numeroBus = numeroBus;
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.capacidadPasajeros = capacidadPasajeros;
        this.estado = "activo";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNumeroBus() { return numeroBus; }
    public void setNumeroBus(String numeroBus) { this.numeroBus = numeroBus; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public int getCapacidadPasajeros() { return capacidadPasajeros; }
    public void setCapacidadPasajeros(int capacidadPasajeros) { this.capacidadPasajeros = capacidadPasajeros; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getUltimaRevision() { return ultimaRevision; }
    public void setUltimaRevision(String ultimaRevision) { this.ultimaRevision = ultimaRevision; }

    public String getProximaRevision() { return proximaRevision; }
    public void setProximaRevision(String proximaRevision) { this.proximaRevision = proximaRevision; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}