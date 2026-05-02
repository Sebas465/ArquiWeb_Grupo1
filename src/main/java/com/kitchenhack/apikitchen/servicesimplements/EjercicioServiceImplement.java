package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.Ejercicio;
import com.kitchenhack.apikitchen.repositories.EjercicioRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IEjercicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Implementación del servicio de ejercicios — delega en el repositorio JPA
@Service
public class EjercicioServiceImplement implements IEjercicioService {

    @Autowired
    private EjercicioRepository ejercicioRepository;

    @Override
    public List<Ejercicio> list() {
        return ejercicioRepository.findAll();
    }

    @Override
    public Ejercicio insert(Ejercicio ejercicio) {
        return ejercicioRepository.save(ejercicio);
    }

    @Override
    public Optional<Ejercicio> listId(Integer id) {
        return ejercicioRepository.findById(id);
    }

    @Override
    public void update(Ejercicio ejercicio) {
        ejercicioRepository.save(ejercicio);
    }

    @Override
    public void delete(Integer id) {
        ejercicioRepository.deleteById(id);
    }
}
