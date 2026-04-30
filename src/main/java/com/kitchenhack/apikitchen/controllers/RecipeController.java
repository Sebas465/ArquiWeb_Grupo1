package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.RecipeDTO;
import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.servicesinterfaces.IRecipeService;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
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
public class RecipeController {

    @Autowired
    private IRecipeService recipeService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<RecipeDTO>> listar() {
        ModelMapper m = new ModelMapper();
        // Cada entidad se convierte a DTO para no devolver directamente la entidad JPA.
        List<RecipeDTO> lista = recipeService.list()
                .stream().map(x -> m.map(x, RecipeDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<Recipe> opt = recipeService.listId(id);
        if (opt.isPresent()) {
            ModelMapper m = new ModelMapper();
            return ResponseEntity.ok(m.map(opt.get(), RecipeDTO.class));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Receta no encontrada");
        }
    }

    @PostMapping("/nuevo")
    public ResponseEntity<RecipeDTO> registrar(@RequestBody RecipeDTO dto) {
        if (dto.getIdAutor() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Optional<Usuario> autorOpt = usuarioService.listId(dto.getIdAutor());
        if (autorOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ModelMapper m = new ModelMapper();
        // El DTO de entrada se convierte a entidad para que el servicio la persista.
        Recipe recipe = m.map(dto, Recipe.class);
        // asegurar que el autor sea la entidad completa
        recipe.setIdAutor(autorOpt.get());
        recipe.setPublished(dto.getPublished() != null ? dto.getPublished() : Boolean.TRUE);

        Recipe saved = recipeService.insert(recipe);
        // La entidad guardada se vuelve a mapear a DTO para la respuesta HTTP.
        RecipeDTO responseDTO = m.map(saved, RecipeDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
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
        ModelMapper m = new ModelMapper();
        List<RecipeDTO> dtos = recipeService.explorePublished(categoria, maxCal)
                .stream().map(x -> m.map(x, RecipeDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
    // ...existing code...
}