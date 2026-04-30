package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.RecipeDTO;
import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.servicesinterfaces.IRecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
public class RecipeController {

    @Autowired
    private IRecipeService recipeService;

    @GetMapping
    public ResponseEntity<?> listar() {
        ModelMapper m = new ModelMapper();
        List<Recipe> recetas = recipeService.list().stream()
                .filter(r -> r.getPublished() != null && r.getPublished())
                .collect(Collectors.toList());

        if (recetas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay recetas disponibles");
        }

        List<RecipeDTO> list = recetas.stream()
                .map(r -> m.map(r, RecipeDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        Optional<Recipe> opt = recipeService.listId(id);
        if (opt.isPresent()) {
            ModelMapper m = new ModelMapper();
            return ResponseEntity.ok(m.map(opt.get(), RecipeDTO.class));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receta no encontrada");
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody RecipeDTO dto) {
        if (dto.getDifficulty() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dificultad inválida");
        }

        String diff = dto.getDifficulty().toLowerCase().trim();
        if (!diff.equals("facil") && !diff.equals("medio") && !diff.equals("dificil")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dificultad inválida");
        }

        ModelMapper m = new ModelMapper();
        try {
            Recipe recipe = m.map(dto, Recipe.class);

            recipe.setId(null);
            recipe.setUltimaActualizacion(java.time.LocalDateTime.now());

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

//    @PutMapping("/{id}")
//    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody RecipeDTO dto) {
//        Optional<Recipe> existente = recipeService.listId(id);
//
//        if (existente.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receta no encontrada");
//        }
//
//        Recipe r = existente.get();
//
//        if (dto.getTitle() != null) r.setTitle(dto.getTitle());
//        if (dto.getDescription() != null) r.setDescription(dto.getDescription());
//        if (dto.getDifficulty() != null) r.setDifficulty(dto.getDifficulty());
//
//        if (dto.getIdAutor() != 0) {
//            r.setIdAutor(dto.getIdAutor());
//        }
//
//        recipeService.update(r);
//
//        return ResponseEntity.ok("Receta actualizada");
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        Optional<Recipe> existente = recipeService.listId(id);
        if (existente.isPresent()) {
            recipeService.delete(id);
            return ResponseEntity.ok("Receta eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receta no encontrada");
        }
    }

}