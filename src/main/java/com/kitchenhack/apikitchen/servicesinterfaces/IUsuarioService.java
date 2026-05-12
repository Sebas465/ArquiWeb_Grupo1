package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Usuario;

import java.util.List;
import java.util.Optional;

/**
 * Contrato (service interface) para operaciones relacionadas con Usuarios.
 *
 * Implementaciones deben delegar en el repositorio JPA y aplicar lógica de
 * negocio mínima (validaciones, conversiones). Los métodos devuelven/reciben
 * la entidad {@link Usuario} directamente.
 */
public interface IUsuarioService {

    /**
     * Lista todos los usuarios registrados.
     * @return lista (posiblemente vacía) de {@link Usuario}
     */
    List<Usuario> list();

    /**
     * Inserta un nuevo usuario en la base de datos.
     * @param usuario entidad a persistir
     * @return la entidad persistida con id generado
     */
    Usuario insert(Usuario usuario);

    /**
     * Persiste cambios en una entidad de usuario existente.
     * @param usuario entidad con cambios
     */
    void update(Usuario usuario);

    /**
     * Busca un usuario por su identificador (Long).
     * @param id identificador
     * @return Optional con el Usuario si existe
     */
    Optional<Usuario> listId(Long id);

    default Optional<Usuario> listId(Integer id) {
        return id == null ? Optional.empty() : listId(id.longValue());
    }

    /**
     * Busca un usuario por email (case-insensitive, ver repo custom).
     * @param email email a buscar
     * @return Optional con el Usuario si existe
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Elimina un usuario por su identificador.
     * @param id identificador del usuario
     */
    void delete(Long id);

    default void delete(Integer id) {
        if (id != null) {
            delete(id.longValue());
        }
    }

    /**
     * Comprueba si existe un usuario con el email dado.
     * Usado para validaciones de unicidad en controladores.
     */
    boolean existsByEmail(String email);

    /**
     * Comprueba si existe un usuario con el username dado.
     */
    boolean existsByUsername(String username);
}

