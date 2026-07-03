# Sistema de Gestión de Eventos Infantiles - FamilyPark


## Descripción del Proyecto

**FamilyPark** es una solución tecnológica web diseñada para automatizar y optimizar la planificación, organización y control de eventos infantiles (cumpleaños) dentro de los recintos de la empresa. 

El proyecto nace para **mitigar y resolver las problemáticas derivadas de la gestión manual**, tales como:
*  Erreores críticos en el cálculo matemático de porciones e insumos alimenticios.
*  Conflictos de agenda, sobreventa de salas y duplicidad de horarios.
*  Desorganización y falta de trazabilidad en las tareas del personal operativo.
*  Retrasos prolongados en la generación y entrega de minutas informativas.

El sistema centraliza el flujo completo desde que un cliente web genera una solicitud de reserva, pasando por la validación del administrador, hasta la automatización de la minuta operativa y asignación de tareas específicas de trabajo.

---

## Características Principales (MVP)

* **Módulo de Clientes:** Formulario dinámico y adaptativo para la solicitud de cumpleaños, selección visual de kits de servicio y cálculo preliminar de invitados.
* **Módulo Administrador:** Bandeja de aprobación/rechazo de reservas y validación automática en tiempo real de la disponibilidad de locales, salas y horarios.
* **Motor Logístico Automatizado:** Algoritmo encargado de calcular de forma exacta el inventario, los insumos y las porciones alimenticias requeridas según el número de asistentes y el kit contratado.
* **Planificación Operativa:** Panel interactivo para la asignación y seguimiento de turnos del personal (Cocina, Monitores infantiles y Personal de Limpieza).

---

## Stack Tecnológico

* **Arquitectura:** Diseño por Capas (Front-end, Back-end y Persistencia) basado en modelamiento UML.
* **Base de Datos:** Modelo Relacional (MER) optimizado con restricciones de integridad y consultas indexadas para evitar la concurrencia en reservas.
* **Versionamiento y Gestión:** Git + GitHub.
* **Framework Ágil:** Scrum (Eventos documentados de Sprint Planning, Daily y Sprint Review).

---

## Aseguramiento de Calidad (QA)

El proyecto cuenta con un plan de verificación basado en **Pruebas Funcionales de Usuario (Caja Negra)**. Los casos de prueba clave han sido ejecutados y validados utilizando matrices de pruebas unificadas para garantizar la fiabilidad del software:

* `Épica 4 (Gestión de Clientes y Reservas):` Validaciones de formularios, alertas de disponibilidad y flujo de aprobación de estados del negocio.
* `Épica 6 (Gestión de Recursos y Minutas):` Pruebas aritméticas automatizadas sobre el cálculo de insumos por invitados y renderizado de la minuta estructurada.

---

## Estructura del Repositorio

* `/docs`: Documentación técnica del proyecto (Informe ERS, Diagramas UML, MER y Acta de Iniciación).
* `/main`: Código fuente de la aplicación (Módulos Front-end y servicios Back-end).

---

## Autor

* **Vicente Antonio Lineros Cabezas** — *Product Owner / Scrum Master / Full-Stack Developer / QA Tester* * **Asignatura:** Evaluación de Software / Ingeniería de Software
* **Sección:** 003D
