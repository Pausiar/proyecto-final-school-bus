# proyecto-final-school-bus
# School-Bus  

Es una aplicación Android que permite a estudiantes y familias seguir el autobús escolar en tiempo real, viendo su ruta y su ubicación actual en un mapa. 
Los conductores pueden enviar su posición, gestionar el recorrido y avisar de llegadas. 
El sistema mejora la seguridad, organización y comunicación del transporte escolar. 
Una posible implementación futura podría ser el sistema de reservas dento del bus, para poder asegurarte un asiento para tus hijos.


## Plan técnico  

 Plataforma: Android Studio – Java 

 BD: SQLite (simple) o PostgreSQL (pro) 

## Servicios: 

Google Maps SDK 

FusedLocationProvider (GPS) 

 ## Roles: 

Conductor 

Estudiante 

Padres 

  ## Módulos: 

Inicio de sesión 

Gestión de usuarios 

Mapa del conductor 

Mapa del estudiante/padre 

Gestión de rutas 

## Integración Firebase

La app ya quedó preparada para usar Firebase Authentication y Firebase Realtime Database desde Android.

Qué quedó conectado:

- Registro de usuario con Email/Password en Firebase Authentication.
- Guardado del perfil del usuario en Realtime Database bajo `users/{uid}`.
- Login con Firebase y carga del perfil remoto en la pantalla de perfil.
- Fallback local a SQLite si Firebase aún no está configurado.

Pasos que faltan en Firebase Console:

1. Crear un proyecto en Firebase.
2. Habilitar Authentication con el proveedor Email/Password.
3. Crear una Realtime Database.

Los valores de Firebase ya están cargados en el proyecto a partir del archivo `google-services.json` proporcionado.

Reglas iniciales recomendadas para Realtime Database:

```json
{
  "rules": {
    "users": {
      "$uid": {
        ".read": "$uid === auth.uid",
        ".write": "$uid === auth.uid"
      }
    }
  }
}
```

No he podido automatizar la creación del proyecto Firebase ni descargar un MCP específico para Firebase porque ese conector no está disponible en este entorno. La alternativa oficial más cercana es el asistente de Android Studio en `Tools > Firebase`.
