package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Interaccion;
import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface IInteraccionService {

    // US-P3-03: registrar interaccion
    Interaccion insert(Interaccion interaccion);

    // US-P3-03: verificar si ya existe la combinacion usuario+receta+tipo
    Optional<Interaccion> findDuplicado(Usuario usuario, Recipe receta, String tipo);

    // US-P3-04: listar interacciones de un usuario
    List<Interaccion> listByUsuario(Usuario usuario);

    // US-P3-04: listar interacciones de un usuario filtradas por tipo
    List<Interaccion> listByUsuarioAndTipo(Usuario usuario, String tipo);
}
