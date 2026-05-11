package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.SuscripcionPlan;

import java.util.Optional;

// Contrato para operaciones sobre suscripciones a planes
public interface ISuscripcionPlanService {

    // Persiste una nueva suscripción
    SuscripcionPlan insert(SuscripcionPlan suscripcion);

    // Busca una suscripción por ID
    Optional<SuscripcionPlan> listId(Integer id);

    // Verifica si el usuario ya tiene suscripción activa al plan
    boolean existeSubscripcionActiva(Long usuarioId, Integer idPlan);
}
