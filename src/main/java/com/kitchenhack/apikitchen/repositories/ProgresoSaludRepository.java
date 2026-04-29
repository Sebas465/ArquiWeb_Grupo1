package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.ProgresoSalud;
import com.kitchenhack.apikitchen.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgresoSaludRepository extends JpaRepository<ProgresoSalud, Integer> {

    // US-P3-02: historial ordenado por fecha DESC
    List<ProgresoSalud> findByIdUsuarioOrderByFechaDesc(Usuario usuario);
}
