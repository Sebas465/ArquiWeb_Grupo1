package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.ProgresoSalud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProgresoSaludRepository extends JpaRepository<ProgresoSalud, Integer> {
    Optional<ProgresoSalud> findByIdUsuario_Id(Integer usuarioId);

    // Filtra mediciones de un usuario en un rango de fechas (US-P4-S2-03)
    List<ProgresoSalud> findByIdUsuario_IdAndFechaBetween(Integer usuarioId, LocalDate inicio, LocalDate fin);
}