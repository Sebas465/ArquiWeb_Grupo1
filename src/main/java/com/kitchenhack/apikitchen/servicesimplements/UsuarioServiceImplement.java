package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.repositories.UsuarioRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImplement implements IUsuarioService {

    @Autowired
    private UsuarioRepository uR;

    @Override
    public Optional<Usuario> listId(Integer id) {
        return uR.findById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return uR.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return uR.existsByUsername(username);
    }

    @Override
    public Usuario insert(Usuario usuario) {
        return uR.save(usuario);
    }

    @Override
    public List<Usuario> list() {
        return uR.findAll();
    }

    @Override
    public void update(Usuario usuario) {
        uR.save(usuario);
    }

    @Override
    public void delete(Integer id) {
        uR.deleteById(id);
    }
}
