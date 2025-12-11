-- ====================================
-- BASE DE DATOS SCHOOL BUS
-- ====================================

-- Eliminar base de datos si existe
DROP DATABASE IF EXISTS school_bus_db;
CREATE DATABASE school_bus_db;
USE school_bus_db;

-- usuarios
CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    password_hash VARCHAR(255) NOT NULL,
    rol ENUM('administrador', 'conductor', 'padre', 'estudiante') NOT NULL,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    foto_perfil VARCHAR(255),
    INDEX idx_email (email),
    INDEX idx_rol (rol)
);

-- ====================================
-- TABLA: estudiantes
-- Información específica de estudiantes
-- ====================================
CREATE TABLE estudiantes (
    id_estudiante INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT UNIQUE NOT NULL,
    grado VARCHAR(20),
    seccion VARCHAR(10),
    direccion_recogida TEXT NOT NULL,
    direccion_entrega TEXT,
    horario_recogida TIME,
    notas_especiales TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

-- padres_estudiantes
CREATE TABLE padres_estudiantes (
    id_relacion INT PRIMARY KEY AUTO_INCREMENT,
    id_padre INT NOT NULL,
    id_estudiante INT NOT NULL,
    relacion ENUM('padre', 'madre', 'tutor', 'otro') NOT NULL,
    es_contacto_primario BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_padre) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(id_estudiante) ON DELETE CASCADE,
    UNIQUE KEY unique_relacion (id_padre, id_estudiante)
);

--conductores
CREATE TABLE conductores (
    id_conductor INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT UNIQUE NOT NULL,
    licencia_numero VARCHAR(50) UNIQUE NOT NULL,
    licencia_vencimiento DATE NOT NULL,
    fecha_contratacion DATE NOT NULL,
    experiencia_anos INT,
    certificaciones TEXT,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);

-- vehiculos
CREATE TABLE vehiculos (
    id_vehiculo INT PRIMARY KEY AUTO_INCREMENT,
    numero_bus VARCHAR(20) UNIQUE NOT NULL,
    placa VARCHAR(20) UNIQUE NOT NULL,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    ano INT,
    capacidad_pasajeros INT NOT NULL,
    color VARCHAR(30),
    estado ENUM('activo', 'mantenimiento', 'fuera_servicio') DEFAULT 'activo',
    ultima_revision DATE,
    proxima_revision DATE,
    notas TEXT,
    INDEX idx_numero_bus (numero_bus),
    INDEX idx_estado (estado)
);

-- rutas
CREATE TABLE rutas (
    id_ruta INT PRIMARY KEY AUTO_INCREMENT,
    nombre_ruta VARCHAR(100) NOT NULL,
    descripcion TEXT,
    tipo_ruta ENUM('ida', 'vuelta', 'especial') NOT NULL,
    distancia_km DECIMAL(6,2),
    duracion_estimada INT COMMENT 'Duración en minutos',
    hora_inicio TIME,
    hora_fin_estimada TIME,
    dias_operacion SET('lunes', 'martes', 'miercoles', 'jueves', 'viernes', 'sabado', 'domingo') DEFAULT 'lunes,martes,miercoles,jueves,viernes',
    activa BOOLEAN DEFAULT TRUE,
    color_mapa VARCHAR(7) DEFAULT '#4A90E2' COMMENT 'Color hexadecimal para el mapa',
    INDEX idx_activa (activa),
    INDEX idx_tipo (tipo_ruta)
);

-- paradas
CREATE TABLE paradas (
    id_parada INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    direccion TEXT NOT NULL,
    latitud DECIMAL(10, 8) NOT NULL,
    longitud DECIMAL(11, 8) NOT NULL,
    referencia VARCHAR(255),
    activa BOOLEAN DEFAULT TRUE,
    INDEX idx_coordenadas (latitud, longitud)
);

-- rutas_paradas
CREATE TABLE rutas_paradas (
    id_ruta_parada INT PRIMARY KEY AUTO_INCREMENT,
    id_ruta INT NOT NULL,
    id_parada INT NOT NULL,
    orden_parada INT NOT NULL COMMENT 'Orden de la parada en la ruta',
    hora_estimada TIME,
    tiempo_espera_minutos INT DEFAULT 2,
    FOREIGN KEY (id_ruta) REFERENCES rutas(id_ruta) ON DELETE CASCADE,
    FOREIGN KEY (id_parada) REFERENCES paradas(id_parada) ON DELETE CASCADE,
    UNIQUE KEY unique_ruta_orden (id_ruta, orden_parada)
);

-- asignaciones
CREATE TABLE asignaciones (
    id_asignacion INT PRIMARY KEY AUTO_INCREMENT,
    id_conductor INT NOT NULL,
    id_vehiculo INT NOT NULL,
    id_ruta INT NOT NULL,
    fecha_asignacion DATE NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE,
    activa BOOLEAN DEFAULT TRUE,
    notas TEXT,
    FOREIGN KEY (id_conductor) REFERENCES conductores(id_conductor) ON DELETE CASCADE,
    FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id_vehiculo) ON DELETE CASCADE,
    FOREIGN KEY (id_ruta) REFERENCES rutas(id_ruta) ON DELETE CASCADE,
    INDEX idx_activa (activa),
    INDEX idx_fecha (fecha_inicio, fecha_fin)
);

-- estudiantes_rutas
CREATE TABLE estudiantes_rutas (
    id_estudiante_ruta INT PRIMARY KEY AUTO_INCREMENT,
    id_estudiante INT NOT NULL,
    id_ruta INT NOT NULL,
    id_parada_recogida INT NOT NULL,
    id_parada_entrega INT,
    fecha_asignacion DATE NOT NULL,
    activa BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(id_estudiante) ON DELETE CASCADE,
    FOREIGN KEY (id_ruta) REFERENCES rutas(id_ruta) ON DELETE CASCADE,
    FOREIGN KEY (id_parada_recogida) REFERENCES paradas(id_parada) ON DELETE CASCADE,
    FOREIGN KEY (id_parada_entrega) REFERENCES paradas(id_parada) ON DELETE CASCADE,
    INDEX idx_activa (activa)
);

-- viajes
CREATE TABLE viajes (
    id_viaje INT PRIMARY KEY AUTO_INCREMENT,
    id_asignacion INT NOT NULL,
    fecha_viaje DATE NOT NULL,
    hora_inicio TIMESTAMP,
    hora_fin TIMESTAMP,
    estado ENUM('programado', 'en_curso', 'completado', 'cancelado') DEFAULT 'programado',
    pasajeros_totales INT DEFAULT 0,
    distancia_recorrida DECIMAL(6,2),
    observaciones TEXT,
    FOREIGN KEY (id_asignacion) REFERENCES asignaciones(id_asignacion) ON DELETE CASCADE,
    INDEX idx_fecha (fecha_viaje),
    INDEX idx_estado (estado)
);

-- asistencia
CREATE TABLE asistencia (
    id_asistencia INT PRIMARY KEY AUTO_INCREMENT,
    id_viaje INT NOT NULL,
    id_estudiante INT NOT NULL,
    id_parada INT NOT NULL,
    hora_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    presente BOOLEAN DEFAULT TRUE,
    observaciones VARCHAR(255),
    FOREIGN KEY (id_viaje) REFERENCES viajes(id_viaje) ON DELETE CASCADE,
    FOREIGN KEY (id_estudiante) REFERENCES estudiantes(id_estudiante) ON DELETE CASCADE,
    FOREIGN KEY (id_parada) REFERENCES paradas(id_parada) ON DELETE CASCADE,
    INDEX idx_viaje_estudiante (id_viaje, id_estudiante)
);

--  ubicaciones_tiempo_real
CREATE TABLE ubicaciones_tiempo_real (
    id_ubicacion INT PRIMARY KEY AUTO_INCREMENT,
    id_viaje INT NOT NULL,
    latitud DECIMAL(10, 8) NOT NULL,
    longitud DECIMAL(11, 8) NOT NULL,
    velocidad DECIMAL(5,2) COMMENT 'Velocidad en km/h',
    direccion DECIMAL(5,2) COMMENT 'Dirección en grados (0-360)',
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_viaje) REFERENCES viajes(id_viaje) ON DELETE CASCADE,
    INDEX idx_viaje_timestamp (id_viaje, timestamp)
);

-- notificaciones
CREATE TABLE notificaciones (
    id_notificacion INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    tipo ENUM('llegada', 'retraso', 'emergencia', 'informacion', 'alerta') NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    mensaje TEXT NOT NULL,
    id_viaje INT,
    leida BOOLEAN DEFAULT FALSE,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_viaje) REFERENCES viajes(id_viaje) ON DELETE SET NULL,
    INDEX idx_usuario_leida (id_usuario, leida),
    INDEX idx_fecha (fecha_envio)
);

-- incidencias
CREATE TABLE incidencias (
    id_incidencia INT PRIMARY KEY AUTO_INCREMENT,
    id_viaje INT,
    id_usuario_reporta INT NOT NULL,
    tipo_incidencia ENUM('mecanica', 'accidente', 'retraso', 'conducta', 'otra') NOT NULL,
    descripcion TEXT NOT NULL,
    gravedad ENUM('baja', 'media', 'alta', 'critica') DEFAULT 'media',
    estado ENUM('reportada', 'en_revision', 'resuelta', 'cerrada') DEFAULT 'reportada',
    fecha_reporte TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_resolucion TIMESTAMP NULL,
    observaciones_resolucion TEXT,
    FOREIGN KEY (id_viaje) REFERENCES viajes(id_viaje) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario_reporta) REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
    INDEX idx_estado (estado),
    INDEX idx_gravedad (gravedad)
);

-- mantenimientos
CREATE TABLE mantenimientos (
    id_mantenimiento INT PRIMARY KEY AUTO_INCREMENT,
    id_vehiculo INT NOT NULL,
    tipo_mantenimiento ENUM('preventivo', 'correctivo', 'revision') NOT NULL,
    descripcion TEXT NOT NULL,
    fecha_programada DATE NOT NULL,
    fecha_realizada DATE,
    costo DECIMAL(10,2),
    mecanico VARCHAR(100),
    estado ENUM('programado', 'en_proceso', 'completado', 'cancelado') DEFAULT 'programado',
    observaciones TEXT,
    FOREIGN KEY (id_vehiculo) REFERENCES vehiculos(id_vehiculo) ON DELETE CASCADE,
    INDEX idx_vehiculo_fecha (id_vehiculo, fecha_programada)
);
