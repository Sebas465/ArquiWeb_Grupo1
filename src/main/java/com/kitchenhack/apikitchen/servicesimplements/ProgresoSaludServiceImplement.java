package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.ProgresoSalud;
import com.kitchenhack.apikitchen.repositories.ProgresoSaludRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IProgresoSaludService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public ProgresoSalud insert(ProgresoSalud progresoSalud) {
        return progresoSaludRepository.save(progresoSalud);
    }

    @Override
    public void update(ProgresoSalud progresoSalud) {
        progresoSaludRepository.save(progresoSalud);
    }

    @Override
    public Optional<ProgresoSalud> listId(Integer id) {
        return progresoSaludRepository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        progresoSaludRepository.deleteById(id);
    }

    @Override
    public List<ProgresoSalud> listByUsuarioAndRango(Long usuarioId, LocalDate inicio, LocalDate fin) {
        return progresoSaludRepository.findByIdUsuario_IdAndFechaBetween(usuarioId, inicio, fin);
    }
}

