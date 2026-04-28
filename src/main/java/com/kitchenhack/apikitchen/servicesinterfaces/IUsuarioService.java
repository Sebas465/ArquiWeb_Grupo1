package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    // Lista todos los usuarios registrados.
    List<Usuario> list();

    // Guarda un nuevo usuario en la base de datos.
    Usuario insert(Usuario usuario);

    // Persiste cambios sobre un usuario existente.
    void update(Usuario usuario);

    // Busca un usuario por su identificador.
    Optional<Usuario> listId(int id);

    // Elimina un usuario por su identificador.
    void delete(int id);
}

