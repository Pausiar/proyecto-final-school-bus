package com.example.school_bus.models;

public class StudentStop {

    private String id;
    private String idEstudiante;
    private String idParada;
    private String tipo; // subida, bajada, ambos
    private boolean activa;

    public StudentStop() {}

    public StudentStop(String idEstudiante, String idParada, String tipo) {
        this.idEstudiante = idEstudiante;
        this.idParada = idParada;
        this.tipo = tipo;
        this.activa = true;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdEstudiante() { return idEstudiante; }
    public void setIdEstudiante(String idEstudiante) { this.idEstudiante = idEstudiante; }

    public String getIdParada() { return idParada; }
    public void setIdParada(String idParada) { this.idParada = idParada; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
}