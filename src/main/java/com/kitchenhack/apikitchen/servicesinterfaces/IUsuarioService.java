package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    Optional<Usuario> listId(Integer id);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Usuario insert(Usuario usuario);
    void update(Usuario usuario);
    void delete(Integer id);
    List<Usuario> list();
}
