package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.repositories.UsuarioRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
/**
 * Implementación de {@link com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService}.
 * Encapsula llamadas al {@link UsuarioRepository} para operaciones CRUD sobre Usuario.
 */
public class UsuarioServiceImplement implements IUsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> list() {
        // Devuelve todos los usuarios registrados
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario insert(Usuario usuario) {
        // Inserta o actualiza (save) la entidad Usuario en la BD
        return usuarioRepository.save(usuario);
    }

    @Override
    public void update(Usuario usuario) {
        // Persistir cambios en la entidad existente
        usuarioRepository.save(usuario);
    }

    @Override
    public Optional<Usuario> listId(Long id) {
        // Buscar por id (Long)
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> listId(Integer id) {
        return id == null ? Optional.empty() : usuarioRepository.findById(id.longValue());
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        // Búsqueda case-insensitive por email a través de JPQL custom
        return usuarioRepository.findByEmailJPQL(email);
    }

    @Override
    public void delete(Long id) {
        // Elimina por id
        usuarioRepository.deleteById(id);
    }

    public void delete(Integer id) {
        if (id != null) {
            usuarioRepository.deleteById(id.longValue());
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        // Comprueba unicidad por email
        return usuarioRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        // Comprueba unicidad por username
        return usuarioRepository.existsByUsername(username);
    }
}

