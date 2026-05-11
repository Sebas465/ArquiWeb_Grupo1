package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.DiaPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Acceso a la tabla dia_plan_item — hereda CRUD básico de JpaRepository
@Repository
public interface DiaPlanItemRepository extends JpaRepository<DiaPlanItem, Integer> {
}
