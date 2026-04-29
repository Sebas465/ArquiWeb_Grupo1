package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.ProgresoSalud;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.repositories.ProgresoSaludRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IProgresoSaludService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProgresoSaludServiceImplement implements IProgresoSaludService {

    @Autowired
    private ProgresoSaludRepository progresoSaludRepository;

    @Override
    public List<ProgresoSalud> list() {
        return progresoSaludRepository.findAll();
    }

    @Override
    public Optional<ProgresoSalud> listId(Integer id) {
        return progresoSaludRepository.findById(id);
    }

    @Override
    public ProgresoSalud insert(ProgresoSalud progresoSalud) {
        return progresoSaludRepository.save(progresoSalud);
    }

    @Override
    public ProgresoSalud update(ProgresoSalud progresoSalud) {
        return progresoSaludRepository.save(progresoSalud);
    }

    @Override
    public void delete(Integer id) {
        progresoSaludRepository.deleteById(id);
    }

    // US-P3-02: historial por usuario ordenado por fecha DESC
    @Override
    public List<ProgresoSalud> listByUsuario(Usuario usuario) {
        return progresoSaludRepository.findByIdUsuarioOrderByFechaDesc(usuario);
    }
}
