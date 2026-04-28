# Guia AGENTS - ArquiWeb_Grupo1

## Panorama general
- Backend Spring Boot 4 + Spring MVC + Spring Data JPA (`src/main/java/com/kitchenhack/apikitchen`).
- Punto de entrada principal: `ApikitchenApplication.java`.
- Hoy existen dos slices funcionales:
  - CRUD de notificaciones (`/notificaciones`) con capa DTO y busqueda de usuario.
  - Endpoint de exploracion de recetas (`/api/recipes/explore`) que devuelve recetas publicadas.

## Arquitectura y flujo de datos
- Flujo de notificaciones:
  - `NotificacionController` -> `INotificacionService` -> `NotificationServiceImplement` -> `NotificacionRepository` / `UsuarioRepository`.
  - El mapeo DTO/Entity es manual en `toDTO` y `toEntity` dentro de `NotificationServiceImplement`.
  - `toEntity` exige un `Usuario` valido (`idUsuario`) y si no existe lanza `RuntimeException`.
- Flujo de recetas:
  - `RecipeController` -> `RecipeServiceImplement` -> `RecipeRepository.findByPublishedTrue()`.
  - Esta ruta responde la entidad JPA `Recipe` directamente (sin capa DTO por ahora).

## Detalles de persistencia que importan
- PostgreSQL es obligatorio (`application.properties` usa `jdbc:postgresql://localhost:5432/kitchenhack`).
- `spring.jpa.hibernate.ddl-auto=update`, por lo que el esquema puede mutar desde las entidades al iniciar.
- El naming de tablas/columnas es mayormente en espanol (`receta`, `usuario`, `notificacion`) con nombres Java mixtos:
  - Ejemplo: `Recipe.title` se mapea a la columna `titulo`.
  - Ejemplo: `Recipe.published` se mapea a `publicada` y se consulta con `findByPublishedTrue()`.

## Convenciones del proyecto (seguir patrones existentes)
- Los paquetes estan separados por responsabilidad y deben respetar estos nombres exactos:
  - `controllers`, `entities`, `repositories`, `dtos`, `servicesinterfaces`, `servicesimplements`.
- El estilo actual usa inyeccion por campo (`@Autowired`) en lugar de constructor.
- Notificaciones expone DTOs; recetas expone entidades.
- Las excepciones actuales son unchecked (`RuntimeException`) y no hay traduccion centralizada.
- `NotificacionController` define `@CrossOrigin(origins = "*")`; no asumir CORS global.

## Flujos de trabajo para desarrollo
- Compilar y ejecutar localmente con Maven Wrapper desde la raiz:
  - `./mvnw.cmd spring-boot:run` (Windows PowerShell).
  - `./mvnw.cmd test`.
- Importante: las pruebas usan `@SpringBootTest` (`ApikitchenApplicationTests`) y levantan el datasource real.
  - Si no existe la BD `kitchenhack`, los tests fallan al iniciar el contexto.
- Hay dependencia de OpenAPI (`springdoc-openapi-starter-webmvc-ui`):
  - Endpoints tipicos: `/swagger-ui/index.html` y `/v3/api-docs`.

## Checklist segura para extender el codigo
- Para cambios de notificaciones, mantener la capa controller -> interface -> implementación y actualizar metodos de mapeo DTO.
- Al agregar metodos de repositorio, priorizar queries derivadas de Spring Data (`findBy...`).
- Si agregas una funcionalidad nueva, decidir explicitamente entre API con DTO (estilo notificaciones) o respuesta de entidad (estilo recetas), y mantener consistencia dentro del feature.
- Si agregas pruebas, considerar un perfil de test o testcontainers/in-memory DB para eliminar la dependencia de la BD local `kitchenhack`.

