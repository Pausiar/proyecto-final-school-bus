package com.example.school_bus.models;

    public class StudentRoute {

        private String id;
        private String idEstudiante;
        private String idRuta;
        private String idParadaSubida;
        private String idParadaBajada;
        private String fechaAsignacion;
        private boolean activa;

        public StudentRoute() {}

        public StudentRoute(String idEstudiante, String idRuta,
                            String idParadaSubida, String idParadaBajada,
                            String fechaAsignacion) {
            this.idEstudiante = idEstudiante;
            this.idRuta = idRuta;
            this.idParadaSubida = idParadaSubida;
            this.idParadaBajada = idParadaBajada;
            this.fechaAsignacion = fechaAsignacion;
            this.activa = true;
        }

        public String getId() { return id; }
        public void setId(String id) { this.id = id; }

        public String getIdEstudiante() { return idEstudiante; }
        public void setIdEstudiante(String idEstudiante) { this.idEstudiante = idEstudiante; }

        public String getIdRuta() { return idRuta; }
        public void setIdRuta(String idRuta) { this.idRuta = idRuta; }

        public String getIdParadaSubida() { return idParadaSubida; }
        public void setIdParadaSubida(String idParadaSubida) { this.idParadaSubida = idParadaSubida; }

        public String getIdParadaBajada() { return idParadaBajada; }
        public void setIdParadaBajada(String idParadaBajada) { this.idParadaBajada = idParadaBajada; }

        public String getFechaAsignacion() { return fechaAsignacion; }
        public void setFechaAsignacion(String fechaAsignacion) { this.fechaAsignacion = fechaAsignacion; }

        public boolean isActiva() { return activa; }
        public void setActiva(boolean activa) { this.activa = activa; }
    }
