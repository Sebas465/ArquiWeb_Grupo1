package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.dtos.RecipeDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IRecipeService {

    List<RecipeDTO> listarTodos();

    RecipeDTO buscarPorId(Long id);

    RecipeDTO crear(RecipeDTO dto);

    RecipeDTO actualizar(Long id, RecipeDTO dto);

    void eliminar(Long id);

    List<RecipeDTO> explorarRecetasPublicadas(Integer categoriaId, BigDecimal maxCal);
}

