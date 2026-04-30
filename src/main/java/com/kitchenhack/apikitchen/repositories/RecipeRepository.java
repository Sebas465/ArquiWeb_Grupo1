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

    List<Recipe> findByPublishedTrueOrderByUltimaActualizacionDesc();

    @Query("SELECT r FROM Recipe r WHERE r.published = true ORDER BY r.ultimaActualizacion DESC")
    List<Recipe> findPublishedRecipes();
}