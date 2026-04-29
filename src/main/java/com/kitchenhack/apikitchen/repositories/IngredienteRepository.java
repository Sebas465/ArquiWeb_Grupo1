package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    // Asumo que el campo se llama tipoIngredienteId por lo que vi en tu controlador
    @Query("SELECT i FROM Ingrediente i WHERE i.tipoIngredienteId = :tipo")
    List<Ingrediente> findByTipo(@Param("tipo") Integer tipo);

    @Query("SELECT i FROM Ingrediente i WHERE LOWER(i.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
    List<Ingrediente> searchByNombre(@Param("nombre") String nombre);

    @Query("SELECT i FROM Ingrediente i WHERE LOWER(i.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) AND i.tipoIngredienteId = :tipo")
    List<Ingrediente> searchByNombreAndTipo(@Param("nombre") String nombre, @Param("tipo") Integer tipo);
}