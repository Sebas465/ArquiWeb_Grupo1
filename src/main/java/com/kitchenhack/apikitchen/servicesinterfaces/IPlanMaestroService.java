package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.PlanMaestro;

import java.util.List;
import java.util.Optional;

// Contrato de negocio para operaciones sobre planes maestros (alimenticio, ejercicio, híbrido)
public interface IPlanMaestroService {

    // Retorna todos los planes maestros disponibles
    List<PlanMaestro> list();

    // Persiste un nuevo plan maestro en la base de datos
    PlanMaestro insert(PlanMaestro plan);

    // Busca un plan maestro por su ID
    Optional<PlanMaestro> listId(Integer id);

    // Actualiza los datos de un plan maestro existente
    void update(PlanMaestro plan);

    void delete(Integer id);

    // US-P4-S2-05 — Filtra planes por tipo
    List<PlanMaestro> listByTipo(String tipoPlan);
}
