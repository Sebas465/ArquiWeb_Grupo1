package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.Etiqueta;
import com.kitchenhack.apikitchen.repositories.EtiquetaRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IEtiquetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EtiquetaServiceImplement implements IEtiquetaService {

    @Autowired
    private EtiquetaRepository etiquetaRepository;

    @Override
    public List<Etiqueta> list() {
        return etiquetaRepository.findAll();
    }

    @Override
    public Etiqueta insert(Etiqueta etiqueta) {
        return etiquetaRepository.save(etiqueta);
    }

    @Override
    public Optional<Etiqueta> listId(Integer id) {
        return etiquetaRepository.findById(id);
    }

    @Override
    public void update(Etiqueta etiqueta) {
        etiquetaRepository.save(etiqueta);
    }

    @Override
    public void delete(Integer id) {
        etiquetaRepository.deleteById(id);
    }
}
