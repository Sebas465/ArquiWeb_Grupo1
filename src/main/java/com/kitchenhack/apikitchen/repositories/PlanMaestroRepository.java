package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.PlanMaestro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanMaestroRepository extends JpaRepository<PlanMaestro, Integer> {

    // US-P4-S2-05 — JPQL: filtra planes por tipo (alimenticio, ejercicio, hibrido)
    @Query("SELECT p FROM PlanMaestro p WHERE p.tipoPlan = :tipoPlan")
    List<PlanMaestro> findByTipoPlan(@Param("tipoPlan") String tipoPlan);
}
