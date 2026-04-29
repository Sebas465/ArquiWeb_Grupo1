package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.Interaccion;
import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.repositories.InteraccionRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IInteraccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteraccionServiceImplement implements IInteraccionService {

    @Autowired
    private InteraccionRepository interaccionRepository;

    // US-P3-03
    @Override
    public Interaccion insert(Interaccion interaccion) {
        return interaccionRepository.save(interaccion);
    }

    // US-P3-03: verificar duplicado
    @Override
    public Optional<Interaccion> findDuplicado(Usuario usuario, Recipe receta, String tipo) {
        return interaccionRepository.findByIdUsuarioAndIdRecetaAndTipo(usuario, receta, tipo);
    }

    // US-P3-04: listar sin filtro de tipo
    @Override
    public List<Interaccion> listByUsuario(Usuario usuario) {
        return interaccionRepository.findByIdUsuario(usuario);
    }

    // US-P3-04: listar filtrado por tipo
    @Override
    public List<Interaccion> listByUsuarioAndTipo(Usuario usuario, String tipo) {
        return interaccionRepository.findByIdUsuarioAndTipo(usuario, tipo);
    }
}
