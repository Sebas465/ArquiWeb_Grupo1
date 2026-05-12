 # KitchenHack API

 API REST en Spring Boot para recetas, ingredientes, usuarios, progreso de salud y notificaciones.

 ## Ejecución

 ```powershell
 .\mvnw.cmd test
 .\mvnw.cmd spring-boot:run
 ```

 ## Endpoints principales

 - `GET /api/recipes` CRUD de recetas
 - `GET /api/recipes/explore?categoria=&max_cal=` recetas publicadas con filtro
 - `GET /ingredientes` búsqueda enriquecida por tipo
 - `GET /ingredientes/{id}` detalle enriquecido de ingrediente
 - `GET /ingredientes/crud` CRUD de ingredientes
 - `GET /usuarios` CRUD de usuarios
 - `GET /progreso-salud` CRUD de progreso de salud
 - `GET /notificaciones` CRUD de notificaciones

 ## Nota
##
 La API externa de ingredientes se configura en `src/main/resources/application.properties` con `external.ingredientes.api`.

