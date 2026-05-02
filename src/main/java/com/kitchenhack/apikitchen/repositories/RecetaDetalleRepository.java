package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.RecetaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecetaDetalleRepository extends JpaRepository<RecetaDetalle, Integer> {
}
