package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.PlanMaestro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Acceso a la tabla plan_maestro — hereda CRUD básico de JpaRepository
@Repository
public interface PlanMaestroRepository extends JpaRepository<PlanMaestro, Integer> {
}
