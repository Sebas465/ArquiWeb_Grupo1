package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.RecipeDTO;
import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.servicesinterfaces.IRecipeService;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
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

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> listar() {
        List<RecipeDTO> list = recipeService.list()
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<Recipe> opt = recipeService.listId(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(toDTO(opt.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receta no encontrada");
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody RecipeDTO dto) {
        if (dto.getIdAutor() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("idAutor es requerido");
        }
        Optional<Usuario> autorOpt = usuarioService.listId(dto.getIdAutor());
        if (autorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor no encontrado");
        }

        Recipe recipe = new Recipe();
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        recipe.setIdAutor(autorOpt.get());
        recipe.setPrepTimeMinutes(dto.getPrepTimeMinutes());
        recipe.setDifficulty(dto.getDifficulty());
        recipe.setPublished(dto.getPublished() != null ? dto.getPublished() : Boolean.TRUE);

        Recipe saved = recipeService.insert(recipe);
        RecipeDTO response = toDTO(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody RecipeDTO dto) {
        Optional<Recipe> existente = recipeService.listId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receta no encontrada");
        }
        Recipe r = existente.get();
        // actualizar campos permitidos según esquema actual de la tabla 'receta'
        r.setTitle(dto.getTitle());
        r.setDescription(dto.getDescription());
        r.setPrepTimeMinutes(dto.getPrepTimeMinutes());
        r.setDifficulty(dto.getDifficulty());
        r.setPublished(dto.getPublished() != null ? dto.getPublished() : r.getPublished());
        if (dto.getIdAutor() != null && (r.getIdAutor() == null || !dto.getIdAutor().equals(r.getIdAutor().getId()))) {
            Optional<Usuario> autorOpt = usuarioService.listId(dto.getIdAutor());
            if (autorOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Autor no encontrado");
            }
            r.setIdAutor(autorOpt.get());
        }
        recipeService.update(r);
        return ResponseEntity.ok("Receta actualizada correctamente");
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

    @GetMapping("/explore")
    public ResponseEntity<List<RecipeDTO>> exploreRecipes(
            @RequestParam(name = "categoria", required = false) Integer categoria,
            @RequestParam(name = "max_cal", required = false) BigDecimal maxCal) {
        List<RecipeDTO> dtos = recipeService.explorePublished(categoria, maxCal)
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    private RecipeDTO toDTO(Recipe r) {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(r.getId());
        dto.setTitle(r.getTitle());
        dto.setDescription(r.getDescription());
        dto.setIdAutor(r.getIdAutor() != null ? r.getIdAutor().getId() : null);
        dto.setPrepTimeMinutes(r.getPrepTimeMinutes());
        dto.setDifficulty(r.getDifficulty());
        dto.setPublished(r.getPublished());
        return dto;
    }
}