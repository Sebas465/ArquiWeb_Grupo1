package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.SistemaEvento;

import java.util.List;
import java.util.Optional;

public interface ISistemaEventoService {

    // Lista todos los eventos del sistema
    List<SistemaEvento> list();

    // Inserta un nuevo evento del sistema
    SistemaEvento insert(SistemaEvento sistemaEvento);

    // Actualiza un evento del sistema existente
    void update(SistemaEvento sistemaEvento);

    // Busca por id
    Optional<SistemaEvento> listId(Integer id);

    // Elimina por id
    void delete(Integer id);
}

