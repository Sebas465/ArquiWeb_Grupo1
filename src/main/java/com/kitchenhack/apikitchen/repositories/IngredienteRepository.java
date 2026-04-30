package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
	List<Ingrediente> findByIdEtiqueta_Id(Integer idEtiqueta);

	@Query("SELECT i FROM Ingrediente i WHERE LOWER(i.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))")
	List<Ingrediente> searchByNombre(@Param("nombre") String nombre);

	@Query("SELECT i FROM Ingrediente i WHERE LOWER(i.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')) " +
		   "AND (:idEtiqueta IS NULL OR i.idEtiqueta.id = :idEtiqueta)")
	List<Ingrediente> searchByNombreAndTipo(@Param("nombre") String nombre, @Param("idEtiqueta") Integer idEtiqueta);
}

