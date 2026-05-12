package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.SuscripcionPlan;

import java.util.List;
import java.util.Optional;

// Contrato para operaciones sobre suscripciones a planes
public interface ISuscripcionPlanService {

    SuscripcionPlan insert(SuscripcionPlan suscripcion);

    Optional<SuscripcionPlan> listId(Integer id);

    boolean existeSubscripcionActiva(Long usuarioId, Integer idPlan);

    // US-P4-S2-04 — Lista suscripciones activas de un usuario
    List<SuscripcionPlan> listActivasByUsuario(Long usuarioId);
}
