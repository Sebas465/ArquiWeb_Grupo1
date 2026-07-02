package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query("SELECT r.difficulty, COUNT(r.id), AVG(r.prepTimeMinutes) FROM Recipe r GROUP BY r.difficulty ORDER BY AVG(r.prepTimeMinutes) DESC")
    List<Object[]> getRecipeStatsByDifficulty();
}