package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.ProgresoSalud;

import java.util.List;
import java.util.Optional;

public interface IProgresoSaludService {

    // Lista todas las entidades ProgresoSalud.
    List<ProgresoSalud> list();

    // Inserta una nueva entidad.
    ProgresoSalud insert(ProgresoSalud progresoSalud);

    // Actualiza una entidad existente.
    void update(ProgresoSalud progresoSalud);

    // Busca por id.
    Optional<ProgresoSalud> listId(Integer id);

    // Elimina por id.
    void delete(Integer id);
}

