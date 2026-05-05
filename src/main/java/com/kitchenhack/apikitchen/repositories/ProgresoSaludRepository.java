package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.ProgresoSalud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgresoSaludRepository extends JpaRepository<ProgresoSalud, Integer> {
    Optional<ProgresoSalud> findByIdUsuario_Id(Long usuarioId);
}

