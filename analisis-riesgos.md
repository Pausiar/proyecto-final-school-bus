# Analisis de Riesgos - School Bus App

## Tabla de Riesgos

| ID | Riesgo | Probabilidad | Impacto | Nivel | Mitigacion |
|----|--------|-------------|---------|-------|------------|
| R1 | Retrasos en el desarrollo por falta de experiencia con Google Maps SDK o GPS | Alta | Alto | Critico | Dedicar tiempo previo a tutoriales y documentacion oficial. Tener alternativa sin mapa en caso extremo |
| R2 | Problemas de compatibilidad entre dispositivos Android (diferentes versiones de API) | Media | Alto | Alto | Testear desde el inicio en varios emuladores (API 21+). Usar componentes estandar de Android |
| R3 | Dificultad para integrar Firebase Cloud Messaging (notificaciones push) | Media | Medio | Medio | Dejarlo como funcionalidad opcional. Usar notificaciones locales como alternativa |
| R4 | Perdida de codigo o trabajo por mala gestion de Git | Baja | Alto | Medio | Hacer commits frecuentes, usar ramas y tener copias de seguridad locales |
| R5 | La base de datos (SQLite/PostgreSQL) no soporta bien los datos de geolocalizacion | Baja | Alto | Medio | Investigar previamente el almacenamiento de coordenadas. Usar tipos de datos adecuados |
| R6 | Falta de tiempo para completar todas las funcionalidades | Alta | Alto | Critico | Priorizar el MVP (horarios, paradas, avisos). Dejar funcionalidades opcionales para ampliaciones futuras |
| R7 | Problemas de rendimiento con el mapa en tiempo real | Media | Medio | Medio | Limitar la frecuencia de actualizacion del GPS. Optimizar las consultas a la BD |
| R8 | Fallo del emulador o del equipo de desarrollo | Baja | Medio | Bajo | Tener acceso a un dispositivo fisico Android como respaldo |
| R9 | Dificultad para gestionar los tres roles (Conductor, Estudiante, Padres) | Media | Medio | Medio | Empezar con un solo rol funcional y ampliar progresivamente |
| R10 | Dependencia de servicios externos (Google Maps API, Firebase) | Baja | Alto | Medio | Tener claves API configuradas desde el inicio. Controlar los limites de uso gratuito |

## Matriz de Riesgos

```
                    |  Bajo Impacto  |  Medio Impacto  |  Alto Impacto
  ------------------|----------------|-----------------|----------------
  Alta Probabilidad |                |                 |  R1, R6
  Media Probabilidad|                |  R3, R7, R9     |  R2
  Baja Probabilidad |  R8            |                 |  R4, R5, R10
```

## Plan de Contingencia para Riesgos Criticos

### R1 - Retrasos por Google Maps SDK / GPS
- **Plan A:** Seguir documentacion oficial y tutoriales paso a paso
- **Plan B:** Implementar una version simplificada del mapa (imagen estatica con paradas marcadas)
- **Plan C:** Mostrar las coordenadas como texto sin mapa interactivo

### R6 - Falta de tiempo
- **Plan A:** Seguir la planificacion del diagrama de Gantt estrictamente
- **Plan B:** Recortar funcionalidades opcionales (notificaciones, rol administrador)
- **Plan C:** Entregar el MVP funcional (consulta de horarios, paradas y avisos) y documentar las ampliaciones futuras