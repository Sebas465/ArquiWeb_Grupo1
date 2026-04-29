package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.SistemaEvento;
import com.kitchenhack.apikitchen.entities.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SistemaEventoRepository extends JpaRepository<SistemaEvento, Integer> {

    List<Recipe> findByPublishedTrue();
}
