package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> list();
    Optional<Usuario> listId(Integer id);
    void delete(Integer id);
    Optional<Usuario> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Usuario insert(Usuario usuario);
    void update(Usuario usuario);
}