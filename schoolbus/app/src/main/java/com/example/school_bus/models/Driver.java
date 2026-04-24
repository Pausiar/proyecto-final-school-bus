package com.example.school_bus.models;

public class Driver {

    private String id;
    private String idUsuario;
    private String licenciaNumero;
    private String licenciaVencimiento;
    private String fechaContratacion;
    private int experienciaAnios;
    private String certificaciones;

    public Driver() {}

    public Driver(String idUsuario, String licenciaNumero,
                  String licenciaVencimiento, String fechaContratacion) {
        this.idUsuario = idUsuario;
        this.licenciaNumero = licenciaNumero;
        this.licenciaVencimiento = licenciaVencimiento;
        this.fechaContratacion = fechaContratacion;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public String getLicenciaNumero() { return licenciaNumero; }
    public void setLicenciaNumero(String licenciaNumero) { this.licenciaNumero = licenciaNumero; }

    public String getLicenciaVencimiento() { return licenciaVencimiento; }
    public void setLicenciaVencimiento(String licenciaVencimiento) { this.licenciaVencimiento = licenciaVencimiento; }

    public String getFechaContratacion() { return fechaContratacion; }
    public void setFechaContratacion(String fechaContratacion) { this.fechaContratacion = fechaContratacion; }

    public int getExperienciaAnios() { return experienciaAnios; }
    public void setExperienciaAnios(int experienciaAnios) { this.experienciaAnios = experienciaAnios; }

    public String getCertificaciones() { return certificaciones; }
    public void setCertificaciones(String certificaciones) { this.certificaciones = certificaciones; }
}