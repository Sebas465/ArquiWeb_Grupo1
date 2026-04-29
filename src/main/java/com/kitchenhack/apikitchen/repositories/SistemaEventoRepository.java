package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.SistemaEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SistemaEventoRepository extends JpaRepository<SistemaEvento, Integer> {
}

