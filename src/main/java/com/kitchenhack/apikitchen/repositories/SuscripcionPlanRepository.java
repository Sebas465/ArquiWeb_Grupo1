package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.SuscripcionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Acceso a la tabla suscripcion_plan
@Repository
public interface SuscripcionPlanRepository extends JpaRepository<SuscripcionPlan, Integer> {

    // Verifica si el usuario ya tiene una suscripción activa al plan indicado
    boolean existsByIdUsuario_IdAndIdPlan_IdAndActivoTrue(Long usuarioId, Integer idPlan);
}
