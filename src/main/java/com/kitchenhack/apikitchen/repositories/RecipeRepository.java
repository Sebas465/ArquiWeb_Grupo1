package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    // Busca todas las recetas donde el campo 'publicada' sea true
    List<Recipe> findByPublishedTrue();

    // DB schema does not include categoryId/totalCalories/averageRating.
    // For exploration we return published recipes ordered by ultimaActualizacion desc.
    List<Recipe> findByPublishedTrueOrderByUltimaActualizacionDesc();

    // Simpler query for published recipes; ignore category/maxCalories filters because
    // those columns don't exist in the current DB schema.
    @Query("SELECT r FROM Recipe r WHERE r.published = true ORDER BY r.ultimaActualizacion DESC")
    List<Recipe> findPublishedRecipes();
}