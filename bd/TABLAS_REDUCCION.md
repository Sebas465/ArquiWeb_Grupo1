# Reducción de Tablas — KitchenHack

**Motivo:** El grupo pasó de 5 a 3 integrantes. La profesora indicó reducir el modelo a 9 tablas acorde al tamaño del equipo.

---

## Tablas que se MANTIENEN (9)

| # | Tabla | Descripción | FK hacia |
|---|-------|-------------|----------|
| 1 | `rol` | Roles del sistema (usuario, nutricionista, entrenador, admin) | — |
| 2 | `etiqueta` | Categorías para ingredientes, recetas y ejercicios | — |
| 3 | `usuario` | Usuarios del sistema con autenticación BCrypt | `rol` |
| 4 | `ingrediente` | Catálogo de ingredientes con info nutricional por 100g | `etiqueta` |
| 5 | `receta` | Recetas publicadas por nutricionistas | `usuario` |
| 6 | `receta_detalle` | Ingredientes y pasos de cada receta | `receta`, `ingrediente` |
| 7 | `ejercicio` | Catálogo de ejercicios con grupo muscular y MET | — |
| 8 | `plan_maestro` | Planes alimenticios, de ejercicio o híbridos | `usuario` |
| 9 | `progreso_salud` | Mediciones de peso, talla e IMC por usuario | `usuario` |

### Diagrama de relaciones (9 tablas)

```
rol ←────────── usuario ──────────→ receta ──────→ receta_detalle
                   │                                      │
                   │                               ingrediente ←── etiqueta
                   │
                   ├──→ plan_maestro
                   │
                   └──→ progreso_salud

ejercicio  (tabla independiente, sin FK, referenciable en planes futuros)
```

---

## Tablas ELIMINADAS (7)

### 1. `contacto_profesional`
**Razón:** Requería lógica de mensajería y estados (pendiente/aceptado/rechazado) entre usuarios. Funcionalidad avanzada que excede el alcance del TF para 3 personas. No tenía controller ni repositorio implementado.

### 2. `perfil_profesional`
**Razón:** Extensión de `usuario` para nutricionistas verificados. La información de especialidad y colegiatura no es necesaria para las funcionalidades core del sistema. No tenía controller ni repositorio implementado.

### 3. `interaccion`
**Razón:** Guardaba favoritos, historial y reseñas de recetas por usuario. Funcionalidad de UX secundaria. Su eliminación simplifica el modelo sin perder las recetas ni los usuarios. Se eliminó también `RecetaPopularidadController` que dependía de esta tabla.

### 4. `dia_plan_item`
**Razón:** Representaba los ítems diarios de un plan maestro (qué receta o ejercicio hacer en cada día). Requería lógica compleja de scheduling. El `plan_maestro` queda simplificado como entidad de cabecera sin detalle de días.

### 5. `suscripcion_plan`
**Razón:** Manejaba la inscripción de usuarios a planes maestros. Dependía de `dia_plan_item` y `progreso_diario`. Al eliminar estas dos, la suscripción perdía sentido.

### 6. `progreso_diario`
**Razón:** Registraba si cada ítem de un plan fue completado en un día. Dependía directamente de `suscripcion_plan` y `dia_plan_item`, ambas eliminadas.

### 7. `sistema_evento`
**Razón:** Sistema de notificaciones internas (logros, recomendaciones IA, alertas). Funcionalidad avanzada propia de un backend de notificaciones. No es parte del flujo principal del TF.

---

## Archivos eliminados del backend

**Entidades:** `ContactoProfesional.java`, `PerfilProfesional.java`, `Interaccion.java`, `DiaPlanItem.java`, `SuscripcionPlan.java`, `ProgresoDiario.java`, `SistemaEvento.java`

**Repositorios:** `InteraccionRepository.java`, `DiaPlanItemRepository.java`, `SuscripcionPlanRepository.java`, `ProgresoDiarioRepository.java`, `SistemaEventoRepository.java`

**Servicios:** `DiaPlanItemServiceImplement.java`, `SuscripcionPlanServiceImplement.java`, `SistemaEventoServiceImplement.java`

**Interfaces:** `IDiaPlanItemService.java`, `ISuscripcionPlanService.java`, `ISistemaEventoService.java`

**Controllers:** `SistemaEventoController.java`, `SuscripcionPlanController.java`, `RecetaPopularidadController.java`

**DTOs:** `DiaPlanItemDTO.java`, `SuscripcionPlanDTO.java`, `SistemaEventoDTO.java`, `RecetaPopularidadDTO.java`

**Endpoint removido de `PlanMaestroController`:** `POST /planes/{id}/items` (dependía de `DiaPlanItem`)

---

## APIs disponibles con las 9 tablas

| Controller | Endpoints |
|-----------|-----------|
| `JwtAuthenticationController` | POST `/login` |
| `UsuarioController` | GET/POST/PUT/DELETE `/usuarios` |
| `RecipeController` | GET/POST/PUT/DELETE `/api/recipes` + detalle |
| `IngredienteController` | GET/POST/PUT/DELETE `/ingredientes` |
| `EjercicioController` | GET/POST/PUT/DELETE `/ejercicios` |
| `PlanMaestroController` | GET/POST/PUT/DELETE `/planes` |
| `ProgresoSaludController` | GET/POST/PUT/DELETE `/progreso-salud` |

---

*Última actualización: 2026-06-23 — Jose Milla*
