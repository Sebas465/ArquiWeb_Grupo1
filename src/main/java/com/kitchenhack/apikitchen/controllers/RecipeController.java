package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.RecetaDetalleDTO;
import com.kitchenhack.apikitchen.dtos.RecipeDTO;
import com.kitchenhack.apikitchen.dtos.RecipeFDDTO;
import com.kitchenhack.apikitchen.dtos.RecipeItemDTO;
import com.kitchenhack.apikitchen.entities.Ingrediente;
import com.kitchenhack.apikitchen.entities.RecetaDetalle;
import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.servicesinterfaces.IIngredienteService;
import com.kitchenhack.apikitchen.servicesinterfaces.IRecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
public class RecipeController {

    @Autowired
    private IRecipeService recipeService;

    @Autowired
    private IIngredienteService ingredienteService;

    // 1. Endpoint para ListarTodo
    @GetMapping
    public ResponseEntity<?> listar() {
        List<Recipe> recetas = recipeService.list();

        if (recetas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay recetas disponibles");
        }

        return ResponseEntity.ok(convertirAListaDto(recetas));
    }

    // 2. Endpoint específico para Filtrar
    @GetMapping("/buscar")
    public ResponseEntity<?> filtrarPorDificultad(@RequestParam(name = "dificultad") String dificultad) {
        List<Recipe> recetas = recipeService.findByDifficulty(dificultad);

        if (recetas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay recetas con esa dificultad");
        }

        return ResponseEntity.ok(convertirAListaDto(recetas));
    }

    private List<RecipeDTO> convertirAListaDto(List<Recipe> lista) {
        ModelMapper m = new ModelMapper();
        return lista.stream()
                .map(r -> m.map(r, RecipeDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorIdCompleto(@PathVariable Integer id) {
        Optional<Recipe> opt = recipeService.listId(id);

        if (opt.isPresent()) {
            ModelMapper m = new ModelMapper();
            RecipeDTO dto = m.map(opt.get(), RecipeDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Receta no encontrada");
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody RecipeDTO dto) {
        ModelMapper m = new ModelMapper();
        try {
            Recipe recipe = m.map(dto, Recipe.class);

            recipe.setId(null);
            recipe.setUltimaActualizacion(java.time.LocalDateTime.now());

            if (dto.getDifficulty() != null) {
                recipe.setDifficulty(dto.getDifficulty());
            }
            if (dto.getPrepTimeMinutes() != null) {
                recipe.setPrepTimeMinutes(dto.getPrepTimeMinutes());
            }
            if (dto.getPublished() != null) {
                recipe.setPublished(dto.getPublished());
            }

            if (dto.getIdAutor() != null) {
                Usuario autor = new Usuario();
                autor.setId(dto.getIdAutor());
                recipe.setIdAutor(autor);
            }

            Recipe saved = recipeService.insert(recipe);
            RecipeDTO responseDTO = m.map(saved, RecipeDTO.class);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);

        } catch (Exception e) {
            Throwable rootCause = org.springframework.core.NestedExceptionUtils.getMostSpecificCause(e);
            String message = rootCause.getMessage();

            if (message != null && (message.contains("id_autor") || message.contains("fk") || message.contains("foreign key"))) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al registrar receta: " + (message != null ? message : "Error desconocido"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody RecipeDTO dto) {
        Optional<Recipe> existente = recipeService.listId(id);

        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receta no encontrada");
        }

        Recipe r = existente.get();

        if (dto.getTitle() != null) r.setTitle(dto.getTitle());
        if (dto.getDescription() != null) r.setDescription(dto.getDescription());
        if (dto.getPrepTimeMinutes() != null) r.setPrepTimeMinutes(dto.getPrepTimeMinutes());
        if (dto.getPublished() != null) r.setPublished(dto.getPublished());

        // Asignación limpia sin validación estricta de minúsculas
        if (dto.getDifficulty() != null) r.setDifficulty(dto.getDifficulty());

        if (dto.getIdAutor() != null) {
            Usuario nuevoAutor = new Usuario();
            nuevoAutor.setId(dto.getIdAutor());
            r.setIdAutor(nuevoAutor);
        }

        r.setUltimaActualizacion(java.time.LocalDateTime.now());

        try {
            recipeService.update(r);
            return ResponseEntity.ok("Receta actualizada con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<Recipe> existente = recipeService.listId(id);
        if (existente.isPresent()) {
            recipeService.delete(id);
            return ResponseEntity.ok("Receta eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receta no encontrada");
        }
    }

    @PostMapping("/{id}/detalle")
    public ResponseEntity<?> agregarDetalle(@PathVariable Integer id, @RequestBody RecetaDetalleDTO dto) {
        Optional<Recipe> recetaOpt = recipeService.listId(id);
        if (recetaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receta no encontrada");
        }

        RecetaDetalle nuevoDetalle = new RecetaDetalle();
        nuevoDetalle.setIdReceta(recetaOpt.get());
        nuevoDetalle.setEsPaso(dto.getEsPaso());
        nuevoDetalle.setOrden(dto.getOrden());

        if (Boolean.FALSE.equals(dto.getEsPaso())) {
            if (dto.getIdIngrediente() == null || dto.getIdIngrediente() == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("id_ingrediente es obligatorio para ingredientes");
            }
            if (dto.getCantidad() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("La cantidad es obligatoria para ingredientes");
            }

            Optional<Ingrediente> ingOpt = ingredienteService.listarPorId(dto.getIdIngrediente());
            if (ingOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("id_ingrediente no encontrado");
            }

            nuevoDetalle.setIdIngrediente(ingOpt.get());
            nuevoDetalle.setCantidad(toBigDecimal(dto.getCantidad()));
            nuevoDetalle.setContenido(null);

        } else {
            if (dto.getContenido() == null || dto.getContenido().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El contenido es obligatorio para pasos de preparación");
            }

            nuevoDetalle.setContenido(dto.getContenido());
            nuevoDetalle.setIdIngrediente(null);
            nuevoDetalle.setCantidad(null);
        }

        RecetaDetalle guardado = recipeService.registrarDetalle(nuevoDetalle);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    private BigDecimal toBigDecimal(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }
}