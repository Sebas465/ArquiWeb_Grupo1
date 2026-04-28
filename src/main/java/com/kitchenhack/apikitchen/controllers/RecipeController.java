package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.RecipeDTO;
import com.kitchenhack.apikitchen.servicesinterfaces.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
public class RecipeController {

    @Autowired
    private IRecipeService recipeService;

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> listar() {
        return ResponseEntity.ok(recipeService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> crear(@RequestBody RecipeDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(recipeService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeDTO> actualizar(@PathVariable Long id, @RequestBody RecipeDTO dto) {
        return ResponseEntity.ok(recipeService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        recipeService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/explore")
    public ResponseEntity<List<RecipeDTO>> exploreRecipes(
            @RequestParam(name = "categoria", required = false) Integer categoria,
            @RequestParam(name = "max_cal", required = false) BigDecimal maxCal) {
        return ResponseEntity.ok(recipeService.explorarRecetasPublicadas(categoria, maxCal));
    }
}