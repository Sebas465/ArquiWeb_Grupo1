package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
	List<Ingrediente> findByTipoIngredienteId(Integer tipoIngredienteId);

	@Query("SELECT i FROM Ingrediente i WHERE LOWER(i.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
	List<Ingrediente> searchByNombre(@Param("nombre") String nombre);

	@Query("SELECT i FROM Ingrediente i WHERE LOWER(i.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) " +
		   "AND (:tipoIngredienteId IS NULL OR i.tipoIngredienteId = :tipoIngredienteId)")
	List<Ingrediente> searchByNombreAndTipo(@Param("nombre") String nombre, @Param("tipoIngredienteId") Integer tipoIngredienteId);
}

