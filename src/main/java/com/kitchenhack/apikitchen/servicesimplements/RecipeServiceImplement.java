package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.dtos.RecipeDTO;
import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.repositories.RecipeRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImplement implements IRecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    private RecipeDTO toDTO(Recipe recipe) {
        RecipeDTO dto = new RecipeDTO();
        dto.setId(recipe.getId());
        dto.setTitle(recipe.getTitle());
        dto.setDescription(recipe.getDescription());
        dto.setImageUrl(recipe.getImageUrl());
        dto.setCategoryId(recipe.getCategoryId());
        dto.setTotalCalories(recipe.getTotalCalories());
        dto.setProteinGrams(recipe.getProteinGrams());
        dto.setCarbsGrams(recipe.getCarbsGrams());
        dto.setFatGrams(recipe.getFatGrams());
        dto.setPrepTimeMinutes(recipe.getPrepTimeMinutes());
        dto.setDifficulty(recipe.getDifficulty());
        dto.setAverageRating(recipe.getAverageRating());
        dto.setPublished(recipe.getPublished());
        return dto;
    }

    private Recipe toEntity(RecipeDTO dto) {
        Recipe recipe = new Recipe();
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        recipe.setImageUrl(dto.getImageUrl());
        recipe.setCategoryId(dto.getCategoryId());
        recipe.setTotalCalories(dto.getTotalCalories());
        recipe.setProteinGrams(dto.getProteinGrams());
        recipe.setCarbsGrams(dto.getCarbsGrams());
        recipe.setFatGrams(dto.getFatGrams());
        recipe.setPrepTimeMinutes(dto.getPrepTimeMinutes());
        recipe.setDifficulty(dto.getDifficulty());
        recipe.setAverageRating(dto.getAverageRating());
        recipe.setPublished(dto.getPublished() != null ? dto.getPublished() : false);
        return recipe;
    }

    @Override
    public List<RecipeDTO> listarTodos() {
        return recipeRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public RecipeDTO buscarPorId(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con id: " + id));
        return toDTO(recipe);
    }

    @Override
    public RecipeDTO crear(RecipeDTO dto) {
        return toDTO(recipeRepository.save(toEntity(dto)));
    }

    @Override
    public RecipeDTO actualizar(Long id, RecipeDTO dto) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con id: " + id));
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        recipe.setImageUrl(dto.getImageUrl());
        recipe.setCategoryId(dto.getCategoryId());
        recipe.setTotalCalories(dto.getTotalCalories());
        recipe.setProteinGrams(dto.getProteinGrams());
        recipe.setCarbsGrams(dto.getCarbsGrams());
        recipe.setFatGrams(dto.getFatGrams());
        recipe.setPrepTimeMinutes(dto.getPrepTimeMinutes());
        recipe.setDifficulty(dto.getDifficulty());
        recipe.setAverageRating(dto.getAverageRating());
        recipe.setPublished(dto.getPublished() != null ? dto.getPublished() : recipe.getPublished());
        return toDTO(recipeRepository.save(recipe));
    }

    @Override
    public void eliminar(Long id) {
        recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con id: " + id));
        recipeRepository.deleteById(id);
    }

    @Override
    public List<RecipeDTO> explorarRecetasPublicadas(Integer categoriaId, BigDecimal maxCal) {
        return recipeRepository.findByPublishedTrueOrderByAverageRatingDesc().stream()
                .filter(recipe -> categoriaId == null || Objects.equals(recipe.getCategoryId(), categoriaId))
                .filter(recipe -> maxCal == null || (recipe.getTotalCalories() != null && recipe.getTotalCalories().compareTo(maxCal) <= 0))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}