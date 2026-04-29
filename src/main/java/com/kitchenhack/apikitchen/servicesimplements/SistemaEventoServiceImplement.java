package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.SistemaEvento;
import com.kitchenhack.apikitchen.repositories.SistemaEventoRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.ISistemaEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SistemaEventoServiceImplement implements ISistemaEventoService {

    @Autowired
    private SistemaEventoRepository sistemaEventoRepository;

    @Override
    public List<SistemaEvento> list() {
        return sistemaEventoRepository.findAll();
    }

    @Override
    public SistemaEvento insert(SistemaEvento sistemaEvento) {
        return sistemaEventoRepository.save(sistemaEvento);
    }

    @Override
    public void update(SistemaEvento sistemaEvento) {
        sistemaEventoRepository.save(sistemaEvento);
    }

    @Override
    public Optional<SistemaEvento> listId(Integer id) {
        return sistemaEventoRepository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        sistemaEventoRepository.deleteById(id);
    }
}

