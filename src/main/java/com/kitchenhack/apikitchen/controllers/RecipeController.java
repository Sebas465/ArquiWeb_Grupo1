package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.RecipeDTO;
import com.kitchenhack.apikitchen.entities.Recipe;
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
    public ResponseEntity<List<RecipeDTO>> listar() {
        ModelMapper m = new ModelMapper();
        List<RecipeDTO> list = recipeService.list()
                .stream().map(r -> m.map(r, RecipeDTO.class)).collect(Collectors.toList());
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
        ModelMapper m = new ModelMapper();
        Recipe recipe = m.map(dto, Recipe.class);
        Recipe saved = recipeService.insert(recipe);
        RecipeDTO response = m.map(saved, RecipeDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody RecipeDTO dto) {
        Optional<Recipe> existente = recipeService.listId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receta no encontrada");
        }
        Recipe r = existente.get();
        // actualizar campos permitidos
        r.setTitle(dto.getTitle());
        r.setDescription(dto.getDescription());
        r.setImageUrl(dto.getImageUrl());
        r.setCategoryId(dto.getCategoryId());
        r.setTotalCalories(dto.getTotalCalories());
        r.setProteinGrams(dto.getProteinGrams());
        r.setCarbsGrams(dto.getCarbsGrams());
        r.setFatGrams(dto.getFatGrams());
        r.setPrepTimeMinutes(dto.getPrepTimeMinutes());
        r.setDifficulty(dto.getDifficulty());
        r.setAverageRating(dto.getAverageRating());
        r.setPublished(dto.getPublished() != null ? dto.getPublished() : r.getPublished());
        recipeService.update(r);
        return ResponseEntity.ok("Receta actualizada correctamente");
    }

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

    @GetMapping("/explore")
    public ResponseEntity<List<RecipeDTO>> exploreRecipes(
            @RequestParam(name = "categoria", required = false) Integer categoria,
            @RequestParam(name = "max_cal", required = false) BigDecimal maxCal) {
        ModelMapper m = new ModelMapper();
        List<RecipeDTO> dtos = recipeService.explorePublished(categoria, maxCal)
                .stream().map(r -> m.map(r, RecipeDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}