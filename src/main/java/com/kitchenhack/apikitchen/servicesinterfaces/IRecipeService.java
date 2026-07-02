package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.RecetaDetalle;
import com.kitchenhack.apikitchen.entities.Recipe;

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

    // US-P2-07
    RecetaDetalle registrarDetalle(RecetaDetalle detalle);

    void deleteDetalle(Integer detalleId);

    void actualizarOrdenDetalle(Integer id, Integer nuevoOrden);

    // 1. Estadísticas de recetas por dificultad
    List<Object[]> getRecipeStatsByDifficulty();

    // En la interfaz:
    List<Object[]> reportTopIngredients();

}