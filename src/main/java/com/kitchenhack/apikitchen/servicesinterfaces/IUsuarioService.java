package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Usuario;
import java.util.List;
import java.util.Optional;

public interface IUsuarioService {
    List<Usuario> list();
    Optional<Usuario> listId(Integer id);
    Usuario insert(Usuario usuario);
    Usuario update(Usuario usuario);
    void delete(Integer id);
}
