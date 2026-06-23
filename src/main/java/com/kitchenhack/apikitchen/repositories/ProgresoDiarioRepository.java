package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.ProgresoDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgresoDiarioRepository extends JpaRepository<ProgresoDiario, Integer> {
    Optional<ProgresoDiario> findByIdSuscripcion_IdAndIdDiaPlanItem_Id(Integer id, Integer item_id);
}