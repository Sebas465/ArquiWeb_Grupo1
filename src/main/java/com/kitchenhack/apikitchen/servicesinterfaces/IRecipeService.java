package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Recipe;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IRecipeService {

    // Lista todas las recetas (entidades).
    List<Recipe> list();

    // Inserta una nueva receta.
    Recipe insert(Recipe recipe);

    // Actualiza una receta existente.
    void update(Recipe recipe);

    // Busca por id.
    Optional<Recipe> listId(Integer id);

    // Elimina por id.
    void delete(Integer id);

    // Explora recetas publicadas (filtrado simple) y devuelve entidades.
    List<Recipe> explorePublished(Integer categoriaId, BigDecimal maxCal);
}

