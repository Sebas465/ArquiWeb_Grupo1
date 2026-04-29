package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Interaccion;
import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InteraccionRepository extends JpaRepository<Interaccion, Integer> {

    // US-P3-03: verificar duplicado usuario+receta+tipo
    Optional<Interaccion> findByIdUsuarioAndIdRecetaAndTipo(Usuario usuario, Recipe receta, String tipo);

    // US-P3-04: listar interacciones de un usuario, opcionalmente filtradas por tipo
    List<Interaccion> findByIdUsuario(Usuario usuario);

    List<Interaccion> findByIdUsuarioAndTipo(Usuario usuario, String tipo);
}
