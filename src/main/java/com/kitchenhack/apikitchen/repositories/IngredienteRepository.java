package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
	List<Ingrediente> findByIdEtiqueta_Id(Integer idEtiquetaId);
}