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
    Optional<Recipe> listId(Long id);

    // Elimina por id.
    void delete(Long id);


}

