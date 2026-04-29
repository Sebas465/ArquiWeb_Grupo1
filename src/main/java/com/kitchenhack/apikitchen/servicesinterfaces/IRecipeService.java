package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Recipe;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IRecipeService {
    List<Recipe> list();
    Optional<Recipe> listId(Long id);
    Recipe insert(Recipe recipe);
    void update(Recipe recipe);
    void delete(Long id);

    // Método para explorar recetas publicadas
    List<Recipe> explorePublished(Integer categoria, BigDecimal maxCal);
}