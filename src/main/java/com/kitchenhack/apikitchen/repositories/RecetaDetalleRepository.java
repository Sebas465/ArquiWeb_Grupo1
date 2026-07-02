package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.RecetaDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaDetalleRepository extends JpaRepository<RecetaDetalle, Integer> {
    @Query("SELECT d.idIngrediente.nombre, COUNT(d.id) FROM RecetaDetalle d WHERE d.esPaso = false GROUP BY d.idIngrediente.nombre ORDER BY COUNT(d.id) DESC")
    List<Object[]> topMostUsedIngredients();
}