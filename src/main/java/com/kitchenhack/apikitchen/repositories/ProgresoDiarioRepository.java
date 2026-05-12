package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.ProgresoDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgresoDiarioRepository extends JpaRepository<ProgresoDiario, Integer> {

    // Busca el registro de progreso de una suscripción para un ítem específico del plan
    Optional<ProgresoDiario> findByIdSuscripcion_IdAndIdDiaPlanItem_Id(Integer idSuscripcion, Integer idDiaPlanItem);
}
