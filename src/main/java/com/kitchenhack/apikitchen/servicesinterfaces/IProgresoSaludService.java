package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.dtos.ProgresoSaludDTO;

import java.util.List;

public interface IProgresoSaludService {

    List<ProgresoSaludDTO> listarTodos();

    ProgresoSaludDTO buscarPorId(Integer id);

    ProgresoSaludDTO crear(ProgresoSaludDTO dto);

    ProgresoSaludDTO actualizar(Integer id, ProgresoSaludDTO dto);

    void eliminar(Integer id);
}

