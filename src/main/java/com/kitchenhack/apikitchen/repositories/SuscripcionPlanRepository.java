package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.SuscripcionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuscripcionPlanRepository extends JpaRepository<SuscripcionPlan, Integer> {

    // Verifica si el usuario ya tiene una suscripción activa al plan indicado
    boolean existsByIdUsuario_IdAndIdPlan_IdAndActivoTrue(Long usuarioId, Integer idPlan);

    // US-P4-S2-04 — JPQL: lista suscripciones activas de un usuario
    @Query("SELECT s FROM SuscripcionPlan s WHERE s.idUsuario.id = :usuarioId AND s.activo = true")
    List<SuscripcionPlan> findByIdUsuario_IdAndActivoTrue(@Param("usuarioId") Long usuarioId);
}
