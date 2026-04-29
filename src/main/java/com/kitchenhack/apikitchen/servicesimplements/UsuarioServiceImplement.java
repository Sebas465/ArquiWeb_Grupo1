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
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> list() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> listId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario insert(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }
}
