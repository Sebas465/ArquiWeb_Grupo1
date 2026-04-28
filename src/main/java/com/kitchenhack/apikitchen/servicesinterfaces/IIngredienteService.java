package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.dtos.IngredienteDTO;
import com.kitchenhack.apikitchen.dtos.IngredienteDetailDTO;

import java.util.List;

public interface IIngredienteService {

    List<IngredienteDTO> listarCrud();

    IngredienteDTO buscarCrudPorId(Long id);

    IngredienteDTO crear(IngredienteDTO dto);

    IngredienteDTO actualizar(Long id, IngredienteDTO dto);

    void eliminar(Long id);

    List<IngredienteDetailDTO> listarPorTipo(Integer tipoIngredienteId);

    IngredienteDetailDTO obtenerPorId(Long id, Integer usuarioId);
}

