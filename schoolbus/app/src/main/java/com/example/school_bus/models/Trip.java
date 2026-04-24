package com.example.school_bus.models;

public class Trip {

    private String id;
    private String idRuta;
    private String idConductor;
    private String idVehiculo;
    private String fechaViaje;
    private String horaInicioReal;
    private String horaFinReal;
    private String estado; // programado, en_curso, completado, cancelado
    private String observaciones;

    public Trip() {}

    public Trip(String idRuta, String idConductor, String idVehiculo,
                String fechaViaje, String estado) {
        this.idRuta = idRuta;
        this.idConductor = idConductor;
        this.idVehiculo = idVehiculo;
        this.fechaViaje = fechaViaje;
        this.estado = estado;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getIdRuta() { return idRuta; }
    public void setIdRuta(String idRuta) { this.idRuta = idRuta; }

    public String getIdConductor() { return idConductor; }
    public void setIdConductor(String idConductor) { this.idConductor = idConductor; }

    public String getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(String idVehiculo) { this.idVehiculo = idVehiculo; }

    public String getFechaViaje() { return fechaViaje; }
    public void setFechaViaje(String fechaViaje) { this.fechaViaje = fechaViaje; }

    public String getHoraInicioReal() { return horaInicioReal; }
    public void setHoraInicioReal(String horaInicioReal) { this.horaInicioReal = horaInicioReal; }

    public String getHoraFinReal() { return horaFinReal; }
    public void setHoraFinReal(String horaFinReal) { this.horaFinReal = horaFinReal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}