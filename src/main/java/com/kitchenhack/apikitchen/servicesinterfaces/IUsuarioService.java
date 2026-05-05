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
    Optional<Usuario> listId(Long id);

    default Optional<Usuario> listId(Integer id) {
        return id == null ? Optional.empty() : listId(id.longValue());
    }

    // Busca un usuario por email.
    Optional<Usuario> findByEmail(String email);

    // Elimina un usuario por su identificador.
    void delete(Long id);

    default void delete(Integer id) {
        if (id != null) {
            delete(id.longValue());
        }
    }

    // Comprueba existencia por email/username para validaciones en controlador.
    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}

