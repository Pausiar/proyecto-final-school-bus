package com.example.school_bus.models;

public class Student {

    private String id;
    private String idUsuario;
    private String nombre;
    private String grado;
    private String seccion;
    private String direccionRecogida;
    private String direccionEntrega;
    private String horarioRecogida;
    private String notasEspeciales;

    public Student() {}

    public Student(String idUsuario, String nombre, String grado,
                   String seccion, String direccionRecogida, String direccionEntrega) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.grado = grado;
        this.seccion = seccion;
        this.direccionRecogida = direccionRecogida;
        this.direccionEntrega = direccionEntrega;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getGrado() { return grado; }
    public void setGrado(String grado) { this.grado = grado; }

    public String getSeccion() { return seccion; }
    public void setSeccion(String seccion) { this.seccion = seccion; }

    public String getDireccionRecogida() { return direccionRecogida; }
    public void setDireccionRecogida(String direccionRecogida) { this.direccionRecogida = direccionRecogida; }

    public String getDireccionEntrega() { return direccionEntrega; }
    public void setDireccionEntrega(String direccionEntrega) { this.direccionEntrega = direccionEntrega; }

    public String getHorarioRecogida() { return horarioRecogida; }
    public void setHorarioRecogida(String horarioRecogida) { this.horarioRecogida = horarioRecogida; }

    public String getNotasEspeciales() { return notasEspeciales; }
    public void setNotasEspeciales(String notasEspeciales) { this.notasEspeciales = notasEspeciales; }
}