package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Interaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// Acceso a la tabla interaccion
@Repository
public interface InteraccionRepository extends JpaRepository<Interaccion, Integer> {

    // Cuenta favoritos agrupados por receta, ordenado de más a menos popular
    @Query("SELECT i.idReceta.id, COUNT(i) FROM Interaccion i WHERE i.tipo = 'favorito' GROUP BY i.idReceta.id ORDER BY COUNT(i) DESC")
    List<Object[]> countFavoritosByReceta();
}
