# Propuesta de Proyecto: School Bus App

## 1. Introducción

El presente documento recoge la **propuesta del proyecto "School Bus App"**, una aplicación móvil nativa para Android orientada a mejorar la **organización, comunicación y acceso a la información** del transporte escolar. La app permitirá a familias, alumnado y responsables consultar información actualizada sobre rutas, horarios, paradas y avisos de incidencias desde sus dispositivos móviles.

El desarrollo se realiza en **Android Studio**, utilizando **Java** para la lógica de la aplicación y **XML** para el diseño de interfaces. La propuesta incluye:  justificación, contexto, necesidades, objetivos SMART, planificación y recursos.

---

## 2. Justificación del proyecto y contextualización

### 2.1 Problemática detectada
La gestión del transporte escolar suele realizarse mediante métodos poco eficientes: WhatsApp, llamadas telefónicas, listados en papel o información dispersa en diferentes canales. Esto genera:
- Dudas frecuentes sobre **horarios y paradas**.
- Dificultades para comunicar **retrasos o incidencias en tiempo real**.
- Falta de un historial claro y organizado. 
- Coordinación deficiente entre familias, alumnado y responsables.

### 2.2 ¿Por qué una app móvil Android?
- **Acceso inmediato**: el móvil es el dispositivo más utilizado por las familias para consultar información rápida.
- **Notificaciones push**: posibilidad de avisar en tiempo real ante cambios o incidencias.
- **Experiencia de usuario nativa**: mejor rendimiento y adaptación al dispositivo que una web responsive.
- **Portabilidad**: la app está siempre disponible sin necesidad de abrir un navegador.

### 2.3 Razones para la elección del proyecto
- Aborda una **necesidad real** del entorno educativo.
- Permite aplicar conocimientos de **desarrollo Android** (Activities, Fragments, RecyclerView, etc.).
- Es viable en tiempo y recursos para un proyecto final.
- Escalable:  se pueden añadir funcionalidades avanzadas (geolocalización, sincronización con servidor, etc.).

---

## 3. Necesidades detectadas (requisitos del problema)

1. **Centralización de información**  
   Consultar horarios, paradas y avisos desde un único lugar.

2. **Comunicación en tiempo real**  
   Recibir notificaciones ante cambios, retrasos o incidencias. 

3. **Accesibilidad móvil**  
   Interfaz clara, intuitiva y optimizada para pantallas de smartphone.

4. **Persistencia de datos**  
   Guardar información localmente (y/o sincronizar con servidor si aplica) para consultarla sin conexión.

5. **Facilidad de actualización**  
   Permitir que responsables publiquen avisos de forma sencilla (si la app incluye esta funcionalidad).

---

## 4. Objetivos del proyecto (SMART)

### Objetivo 1: Desarrollar una app Android funcional para consulta de información del transporte escolar
- **Específico:** Crear una aplicación nativa Android con al menos 3 pantallas principales:  *Inicio/Dashboard*, *Horarios y paradas* y *Avisos*.
- **Medible:** La app debe ejecutarse correctamente en Android Studio y en dispositivo físico/emulador (API mínima 21+).
- **Alcanzable:** Usando Java, XML, y componentes Android estándar (Activity, RecyclerView, Intent, etc.).
- **Relevante:** Cumple con la necesidad de centralizar información. 
- **Temporal:** Completado antes de la fase de pruebas (semana 5).

### Objetivo 2: Implementar persistencia de datos (local y/o remota)
- **Específico:** Almacenar información de horarios, paradas y avisos mediante [SQLite local / API REST + base de datos remota — *ajustar según tu caso*].
- **Medible:** Realizar operaciones CRUD (al menos consulta y creación de avisos).
- **Alcanzable:** Con SQLiteOpenHelper o Room (si es local) o Retrofit/Volley (si es remota).
- **Relevante:** Permite mantener datos actualizados y accesibles sin conexión (si aplica).
- **Temporal:** Implementado en la fase de desarrollo backend/persistencia (semana 4-5).

### Objetivo 3: Integrar sistema de notificaciones (opcional según alcance)
- **Específico:** Mostrar notificaciones push o locales cuando se publique un nuevo aviso.
- **Medible:** Al menos una notificación de prueba visible en la barra de estado del dispositivo.
- **Alcanzable:** Con Firebase Cloud Messaging (FCM) o NotificationCompat.
- **Relevante:** Mejora la comunicación inmediata ante incidencias.
- **Temporal:** Implementado antes de la fase de integración (semana 6).

### Objetivo 4: Garantizar una interfaz intuitiva y adaptada a móvil
- **Específico:** Diseñar layouts con Material Design, navegación clara y usabilidad en distintos tamaños de pantalla. 
- **Medible:** Sin errores de diseño en emuladores de 5", 6" y tablet.
- **Alcanzable:** Con ConstraintLayout, RecyclerView, Material Components. 
- **Relevante:** Los usuarios finales son familias con distintos dispositivos.
- **Temporal:** Validado durante la fase de pruebas (semana 6-7).

### Objetivo 5: Documentar el desarrollo y entregar memoria final completa
- **Específico:** Redactar memoria con justificación, requisitos, diseño, desarrollo, pruebas y conclusiones. 
- **Medible:** Documento con capturas de pantalla, diagramas de navegación, código comentado y manual de instalación.
- **Alcanzable:** A partir del trabajo realizado y control de versiones (Git/GitHub).
- **Relevante:** Requisito académico. 
- **Temporal:** Finalizado en la semana 8.

---

## 5. Alcance del proyecto y limitaciones

### Alcance (MVP propuesto)
- Visualización de **horarios y paradas** organizados por ruta.
- Consulta de **avisos** (lista con título, descripción, fecha).
- [Opcional] Publicación de avisos desde la app (si se incluye rol administrador).
- [Opcional] Notificaciones básicas ante nuevo aviso. 
- Persistencia de datos (local con SQLite o remota con API REST).

### Posibles ampliaciones futuras
- Geolocalización y mapa con paradas en tiempo real.
- Roles de usuario (conductor/familia/administrador) con autenticación.
- Historial de incidencias y estadísticas de puntualidad.
- Sincronización en la nube (Firebase Realtime Database o similar).
- Versión iOS (si se reescribe en Flutter/React Native).

### Limitaciones
- Alcance ajustado al tiempo disponible del proyecto final.
- Funcionalidades avanzadas (tracking GPS en tiempo real) requieren infraestructura adicional.
- Acceso a datos reales del servicio de transporte escolar (puede simularse con datos de ejemplo).

---

## 6. Planificación y temporalización orientativa

| Fase | Descripción | Duración estimada | Entregables |
|------|------------|------------------|------------|
| 1. Análisis y requisitos | Definición del problema, usuarios objetivo, alcance y funcionalidades | 1 semana | Documento de requisitos + casos de uso |
| 2. Diseño | Wireframes de pantallas, diseño UI/UX, diagrama de navegación y modelo de datos | 1 semana | Prototipo en papel/Figma + modelo de BD |
| 3. Desarrollo Android (UI) | Creación de Activities/Fragments, layouts XML, RecyclerView, navegación | 2 semanas | Interfaz funcional navegable (sin datos reales aún) |
| 4. Desarrollo lógica y persistencia | Implementación de SQLite/API, operaciones CRUD, lógica de negocio | 2 semanas | App con datos persistentes y funcionalidades principales |
| 5. Integración y pruebas | Pruebas en emulador y dispositivo físico, corrección de bugs | 1 semana | Versión estable + informe de pruebas |
| 6. Documentación y entrega | Memoria final, manual de usuario/instalación, conclusiones | 1 semana | Documento final + APK entregable |

**Hitos principales**
- Hito 1: Requisitos cerrados y diseño aprobado (fin semana 2).
- Hito 2: Prototipo navegable (fin semana 4).
- Hito 3: Funcionalidades principales completas (fin semana 6).
- Hito 4: App final probada y documentada (fin semana 8).

---

## 7. Recursos necesarios

### 7.1 Recursos materiales
- Ordenador con capacidad para ejecutar Android Studio (mínimo 8 GB RAM recomendado).
- Dispositivo Android físico (para pruebas reales) o emulador AVD.
- Conexión a Internet (para descargar dependencias, acceso a Firebase si aplica, etc.).

### 7.2 Recursos software y tecnológicos
- **IDE:** Android Studio (última versión estable).
- **Lenguaje:** Java (Android SDK API 21+).
- **Control de versiones:** Git y GitHub (`Pausiar/proyecto-final-school-bus`).
- **Persistencia de datos:** SQLite con Room o SQLiteOpenHelper [o API REST con Retrofit + servidor backend si aplica].
- **Notificaciones:** Firebase Cloud Messaging (FCM) o NotificationCompat local.
- **Diseño:** Material Design Components, ConstraintLayout, RecyclerView. 
- **Herramientas de prototipado:** Figma, draw.io o Adobe XD. 
- **Documentación:** Markdown (GitHub), Google Docs o Word.

### 7.3 Recursos humanos
- Equipo de desarrollo (alumnado): reparto de tareas (UI, lógica, BD, pruebas, documentación).
- Tutoría/supervisión del profesorado para revisión de avances y memoria.

---

## 8. Metodología de desarrollo (opcional, según requisitos de memoria)

Se seguirá un enfoque **iterativo e incremental**, con ciclos cortos de desarrollo, pruebas y feedback.  Esto permite: 
- Validar cada funcionalidad antes de avanzar.
- Detectar errores de forma temprana.
- Ajustar alcance si es necesario. 

**Buenas prácticas aplicadas:**
- Uso de Git con commits descriptivos y ramas (por ejemplo: `feature/pantalla-avisos`, `fix/bug-recyclerview`).
- Código comentado y organizado en paquetes lógicos (UI, modelos, BD, utilidades).
- Pruebas manuales en distintos dispositivos/emuladores. 

---

## 9. Conclusión

El proyecto "School Bus App" responde a una necesidad real del entorno educativo y aprovecha las ventajas del desarrollo nativo Android para ofrecer una experiencia de usuario óptima. La propuesta incluye objetivos SMART, planificación temporal, recursos necesarios y un alcance claro, estableciendo una base sólida para el desarrollo y la memoria final del proyecto.

---

### Anexo: Capturas de pantalla (ejemplo de estructura para la memoria)
- Pantalla de inicio / Dashboard
- Pantalla de horarios y paradas
- Pantalla de avisos (RecyclerView)
- Pantalla de detalle de aviso
- Notificación push (captura de sistema)

---

### Anexo: Diagrama de navegación (a incluir en memoria)
```
[Splash Screen] → [Dashboard]
                      ↓
       ┌──────────────┼──────────────┐
       ↓              ↓              ↓
  [Horarios]     [Paradas]      [Avisos]
                                   ↓
                            [Detalle Aviso]
```

---

**Nota:** Algunos apartados (como diagramas UML, casos de uso detallados o requisitos funcionales/no funcionales) pueden añadirse si la memoria lo exige.  Este documento es el núcleo de la propuesta. 
