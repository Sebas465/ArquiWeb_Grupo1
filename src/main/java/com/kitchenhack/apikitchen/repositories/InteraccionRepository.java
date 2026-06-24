package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Interaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface InteraccionRepository extends JpaRepository<Interaccion, Integer> {

    @Query("SELECT i.receta.id, COUNT(i) FROM Interaccion i WHERE i.tipo = 'favorito' GROUP BY i.receta.id ORDER BY COUNT(i) DESC")
    List<Object[]> countFavoritosByReceta();
}