package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.DiaPlanItem;

import java.util.Optional;

// Contrato para operaciones sobre ítems de días del plan
public interface IDiaPlanItemService {

    // Persiste un nuevo ítem en la base de datos
    DiaPlanItem insert(DiaPlanItem item);

    // Busca un ítem por su ID
    Optional<DiaPlanItem> listId(Integer id);

    // Elimina un ítem por su ID
    void delete(Integer id);
}
