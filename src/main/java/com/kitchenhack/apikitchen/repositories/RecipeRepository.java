package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // Busca todas las recetas donde el campo 'publicada' sea true
    List<Recipe> findByPublishedTrue();
}