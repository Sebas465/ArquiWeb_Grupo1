# AGENTS.md - Guía de Desarrollo KitchenHack API

## Descripción General del Proyecto
**KitchenHack** es una API REST de Spring Boot para gestión de recetas y notificaciones de usuarios. Proporciona endpoints para explorar recetas publicadas y gestionar notificaciones de usuarios con operaciones CRUD completas.

- **Framework**: Spring Boot 4.0.5
- **Base de Datos**: PostgreSQL (localhost:5432/kitchenhack)
- **Lenguaje**: Java 17
- **Puerto API**: 8080
- **Documentación API**: http://localhost:8080/swagger-ui.html (SpringDoc OpenAPI)

---

## Arquitectura & Patrones Clave

### Arquitectura por Capas
```
Controller (@RestController)
    ↓
Service Interface (INotificacionService)
    ↓
Service Implementation (@Service)
    ↓
Repository (JpaRepository)
    ↓
Entity (@Entity)
```

### Patrón Crítico: División Interfaz de Servicio + Implementación
**NO es un error** - mantenido separado intencionalmente:
- `servicesinterfaces/INotificacionService.java` - contrato
- `servicesimplements/NotificationServiceImplement.java` - implementa interfaz
- Aplica a **Notificacion** (notificaciones) solamente; servicio de Recipe aún no tiene interfaz

### Patrón DTO con Mapeo Manual
Los servicios incluyen métodos privados convertidores (no MapStruct):
```java
private NotificacionDTO toDTO(Notificacion n) { ... }
private Notificacion toEntity(NotificacionDTO dto) { ... }
```
Las relaciones de entidades (como `Usuario`) se resuelven durante la conversión. Al implementar nuevos servicios, sigue este patrón.

### Inconsistencia Intencional en Nomenclatura REST (OBSERVAR)
- **Notificaciones**: `/notificaciones` (Español)
- **Recetas**: `/api/recipes` (prefijo en inglés + ruta)
Esta convención mixta existe en el codebase; mantenla para consistencia.

---

## Dos Módulos Principales

### 1. Notificaciones (`Notificacion` Entity)
**Completamente implementado con CRUD**
- Entidad: relacionada con `Usuario` vía `@ManyToOne`
- Interfaz de servicio: `INotificacionService` (5 métodos)
- Controlador: `NotificacionController` - TODOS los métodos funcionan
  - GET `/notificaciones` - listar todas
  - GET `/notificaciones/{id}` - por ID
  - POST `/notificaciones` - crear
  - PUT `/notificaciones/{id}` - actualizar
  - DELETE `/notificaciones/{id}` - eliminar
- DTO: `NotificacionDTO` - refleja campos de la entidad
- Excepciones: lanza `RuntimeException` con mensaje (sin manejador de excepciones personalizado aún)

**Clave: El manejo de excepciones es básico** - `findById().orElseThrow()` lanza RuntimeException

### 2. Recetas (`Recipe` Entity)
**Parcialmente implementado - PUNTO DE EXPANSIÓN**
- Entidad: datos nutricionales (calorías, proteína, carbohidratos, grasas) como `BigDecimal`
- Repositorio: tiene query personalizado `findByPublishedTrue()` (patrón de named query)
- Servicio: implementación básica, SIN interfaz aún
  - Solo existe método `getPublishedRecipes()`
- Controlador: un único endpoint `/api/recipes/explore` - devuelve recetas publicadas
- **Sin CRUD completo** - esta es el área de crecimiento principal

**¿Por qué BigDecimal para nutrición?** Precisión para cálculos nutricionales, no Float/Double.

---

## Capa de Base de Datos & Patrones de Consulta

### Consultas Personalizadas en Repositorio
Los repositorios extienden `JpaRepository<Entity, KeyType>`:
```java
@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByPublishedTrue();  // Convención de nombrado - Spring genera SQL
}
```
**Patrón**: Nombres de métodos como `findBy<CampoNombre><Condicion>` generan consultas automáticamente.

### Mapeo de Tabla de Entidad
- Nombre de Entidad → tabla BD: `@Table(name = "...")`
  - `Recipe` → `receta` (Español)
  - `Usuario` → `usuario`
  - `Notificacion` → `notificacion`
- Nombrado de columnas: `@Column(name = "...")` con snake_case (ej: `id_usuario`, `fecha_envio`)

### Relaciones
- `Notificacion` tiene `@ManyToOne Usuario` (muchas notificaciones por usuario)
- Sin referencia hacia atrás explícita desde Usuario (unidireccional)
- Al consultar, Usuario debe existir o `.orElseThrow()` en servicio

---

## Comandos de Build & Despliegue

### Flujo de Desarrollo
```bash
# Construir el proyecto
./mvnw clean install

# Ejecutar localmente
./mvnw spring-boot:run
# La API inicia en http://localhost:8080
```

### Verificar API
- **Actuator Principal**: http://localhost:8080/actuator
- **UI Swagger/OpenAPI**: http://localhost:8080/swagger-ui.html
- **Lista de notificaciones**: curl http://localhost:8080/notificaciones
- **Explorar recetas**: curl http://localhost:8080/api/recipes/explore

### Configuración de Base de Datos
En `application.properties`:
- Driver: PostgreSQL
- URL: `jdbc:postgresql://localhost:5432/kitchenhack`
- Esquema: auto-actualización vía Hibernate (`spring.jpa.hibernate.ddl-auto=update`)
- Logging SQL: habilitado (`spring.jpa.show-sql=true`)

---

## Comunicación Entre Componentes

### Dependencias Importantes
- **Notificacion** depende de **Usuario** - UsuarioRepository inyectado en NotificationServiceImplement
- **Recipe** es independiente (puede publicarse/no publicarse por si sola)
- Controladores usan `@Autowired` para inyección de servicios (inyección por campo, no constructor)

### Ejemplo de Flujo de Datos (Crear Notificación)
1. **Controlador** recibe `NotificacionDTO`
2. **Servicio** llama `usuarioRepository.findById(dto.getIdUsuario())` - DEBE EXISTIR
3. `toEntity()` convierte DTO → crea Notificacion con referencia a Usuario
4. **Repositorio** guarda y devuelve entidad
5. **Servicio** convierte DTO nuevamente y devuelve

Si Usuario no existe: `RuntimeException("Usuario no encontrado con id: ...")`

---

## Patrones de Generación de Código & Extensión

### Agregar Nuevo Módulo (ej: Servicio de Ingredientes)
Sigue esta estructura exacta:
```
src/main/java/com/kitchenhack/apikitchen/
├── entities/Ingredient.java              # @Entity con columnas JPA
├── repositories/IngredientRepository.java # extiende JpaRepository
├── dtos/IngredientDTO.java                # POJO con getters/setters
├── servicesinterfaces/IIngredientService.java  # Interfaz
├── servicesimplements/IngredientServiceImplement.java  # Implementa + toDTO/toEntity
└── controllers/IngredientController.java  # @RestController con endpoints CRUD
```

### Convenciones de Nombrado
- **Entidades**: CamelCase, singular (Recipe, Usuario, Notificacion)
- **DTOs**: mismo nombre + sufijo "DTO"
- **Servicios**: Interfaz = `I<Nombre>Service`, Implementación = `<Nombre>ServiceImplement`
- **Controladores**: `<Nombre>Controller`
- **Repositorios**: `<Nombre>Repository`
- **Tablas BD**: snake_case, español donde corresponda

### Uso de Métodos HTTP
- GET → operaciones de lectura → `findAll()`, `findById()`
- POST → crear → respuesta `HttpStatus.CREATED`
- PUT → actualización completa
- DELETE → eliminar, devuelve `ResponseEntity.noContent()`
- Todos usan `ResponseEntity<T>` para seguridad de tipos

---

## Limitaciones Conocidas & TODOs

1. **CRUD de Recipe incompleto** - solo lectura (explore) implementada, sin POST/PUT/DELETE
2. **Sin manejo global de excepciones** - RuntimeException usado directamente (sin `@ControllerAdvice`)
3. **Sin validación de entrada** - DTOs carecen de `@NotNull`, `@Size`, etc.
4. **Sin paginación** - todas las consultas devuelven listas completas
5. **Sin Spring Security** - sin autenticación/autorización implementada
6. **RecipeServiceImplement sin interfaz** - inconsistente con patrón de Notificacion
7. **Mapeo manual de DTO** - considerar MapStruct para modelos complejos

---

## CORS & Integración con Frontend
- **Controlador de notificaciones** tiene `@CrossOrigin(origins = "*")` - permite todos los orígenes
- **Controlador de recetas** carece de anotación CORS - puede necesitar `@CrossOrigin` si el frontend lo llama
- Agrega `@CrossOrigin` a nuevos controladores para solicitudes cross-origin desde frontend

---

## Testing & Debugging
- Clase de test: `ApikitchenApplicationTests.java` (mínima, probablemente vacía)
- Usa `spring.jpa.show-sql=true` en `application.properties` para depuración de consultas
- Prueba endpoints: usa curl o Postman contra `http://localhost:8080`
- Verifica logs de PostgreSQL si hay discrepancias entre entidad/tabla

---

## Referencia Rápida: Dónde Agregar Features

| Necesidad | Ubicación |
|------|----------|
| Nuevo endpoint | `controllers/<Nombre>Controller.java` |
| Nueva tabla/entidad | `entities/<Nombre>.java` + `repositories/<Nombre>Repository.java` |
| Lógica de negocio | `servicesimplements/<Nombre>ServiceImplement.java` |
| Contrato API | `servicesinterfaces/I<Nombre>Service.java` |
| Manejo global de errores | Crear `com/kitchenhack/apikitchen/exception/GlobalExceptionHandler.java` con `@ControllerAdvice` |
| Consultas a BD | Métodos personalizados en Repository siguiendo nombrado Spring Data |

