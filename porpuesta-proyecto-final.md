# Proyecto Final - School Bus

## 📋 Descripción General

School Bus es una aplicación web diseñada para gestionar y optimizar el transporte escolar, proporcionando una solución integral para la administración de rutas, seguimiento de estudiantes y comunicación entre padres, conductores y administradores escolares.

## 🎯 Objetivo del Proyecto

Desarrollar una plataforma digital que facilite la gestión del transporte escolar, mejorando la seguridad, eficiencia y comunicación entre todos los involucrados en el proceso de transporte estudiantil.

## 🚀 Características Principales

### Para Administradores
- Gestión de rutas escolares
- Asignación de conductores y vehículos
- Monitoreo en tiempo real de los recorridos
- Administración de usuarios y permisos

### Para Padres/Tutores
- Seguimiento en tiempo real de la ubicación del autobús
- Notificaciones sobre llegadas y salidas
- Comunicación directa con conductores
- Historial de viajes
- Alertas de emergencia

### Para Conductores
- Visualización de rutas asignadas
- Lista de estudiantes por ruta
- Registro de asistencia
- Comunicación con padres y administración
- Reportes de incidencias

### Panel Principal
- Vista general de rutas activas
- Estadísticas del día
- Notificaciones recientes
- Accesos rápidos a funciones principales

### Gestión de Rutas
- Crear nueva ruta
- Editar rutas existentes
- Asignar estudiantes
- Programar horarios

### Casos de Prueba Principales
1. **Autenticación de usuarios**
2. **Gestión de rutas**
3. **Sistema de seguimiento GPS**
4. **Notificaciones en tiempo real**

---

## 📝 Tareas Básicas para Crear la App

### Configuración Inicial del Proyecto
- Crear proyecto en Android Studio con Java / Pausiar (en progreso - Issue #3)
- Configurar Gradle y dependencias básicas / Pausiar
- Integrar SDK de mapas (OpenStreetMap u otra alternativa) / Mincu
- Configurar FusedLocationProvider para GPS / Mincu
- Configurar base de datos SQLite / Khadija

### Módulo de Inicio de Sesión
- Crear Activity de Login / Pausiar
- Diseñar layout XML del login / Khadija
- Implementar validación de campos / Mincu
- Conectar login con base de datos / Pausiar
- Crear Activity de registro de usuario / Khadija

### Módulo de Gestión de Usuarios
- Crear modelo de datos Usuario (Conductor, Estudiante, Padre) / Pausiar
- Crear Activity para perfil de usuario / Mincu
- Implementar edición de datos personales / Khadija
- Implementar roles y permisos / Pausiar

### Módulo de Mapa del Conductor
- Crear Activity del mapa para conductor / Mincu
- Mostrar ruta asignada en el mapa / Mincu
- Implementar envío de ubicación en tiempo real / Pausiar
- Crear botón de inicio/fin de recorrido / Khadija
- Implementar lista de estudiantes por ruta / Khadija

### Módulo de Mapa del Estudiante/Padre
- Crear Activity del mapa para estudiante/padre / Mincu
- Mostrar ubicación del autobús en tiempo real / Pausiar
- Mostrar ruta y paradas del autobús / Mincu
- Implementar tiempo estimado de llegada / Khadija
- Crear sistema de notificaciones de llegada / Pausiar

### Módulo de Gestión de Rutas
- Crear Activity para gestión de rutas / Khadija
- Implementar creación de nueva ruta / Pausiar
- Implementar edición de rutas existentes / Mincu
- Implementar asignación de estudiantes a rutas / Khadija
- Implementar asignación de conductores a rutas / Mincu

### Interfaz de Usuario (UI)
- Diseñar navegación principal (Bottom Navigation) / Khadija
- Crear layouts para cada pantalla / Mincu
- Implementar menú de configuración / Pausiar
- Aplicar estilos y colores de la app / Khadija

### Pruebas y Finalización
- Probar autenticación de usuarios / Pausiar
- Probar seguimiento GPS / Mincu
- Probar notificaciones / Khadija
- Corregir errores encontrados / Todos
- Generar APK final / Pausiar