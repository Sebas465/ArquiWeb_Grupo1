package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // Busca todas las recetas donde el campo 'publicada' sea true
    List<Recipe> findByPublishedTrue();

    List<Recipe> findByPublishedTrueOrderByAverageRatingDesc();

    @Query("SELECT r FROM Recipe r WHERE r.published = true " +
           "AND (:categoryId IS NULL OR r.categoryId = :categoryId) " +
           "AND (:maxCalories IS NULL OR r.totalCalories <= :maxCalories) " +
           "ORDER BY r.averageRating DESC")
    List<Recipe> findPublishedRecipes(@Param("categoryId") Integer categoryId,
                                      @Param("maxCalories") BigDecimal maxCalories);
}