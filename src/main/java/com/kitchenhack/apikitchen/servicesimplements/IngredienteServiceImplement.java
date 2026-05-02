package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.Ingrediente;
import com.kitchenhack.apikitchen.repositories.IngredienteRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IIngredienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredienteServiceImplement implements IIngredienteService {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Override
    public List<Ingrediente> list() {
        return ingredienteRepository.findAll();
    }

    @Override
    public Ingrediente insert(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    @Override
    public void update(Ingrediente ingrediente) {
        ingredienteRepository.save(ingrediente);
    }

    @Override
    public Optional<Ingrediente> listId(Long id) {
        return ingredienteRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        ingredienteRepository.deleteById(id);
    }

    @Override
    public List<Ingrediente> findByTipo(Integer tipo) {
        return ingredienteRepository.findByIdEtiqueta_Id(tipo);
    }

    @Override
    public List<Ingrediente> searchByNombre(String nombre) {
        return ingredienteRepository.searchByNombre(nombre);
    }

    @Override
    public List<Ingrediente> searchByNombreAndTipo(String nombre, Long tipoIngredienteId) {
        return ingredienteRepository.searchByNombreAndTipo(nombre, tipoIngredienteId);
    }

    @Override
    public boolean existePorId(int id) {
        return ingredienteRepository.existsById((long) id);
    }

    @Override
    public Optional<Ingrediente> listarPorId(int id) {
        return ingredienteRepository.findById((long) id);
    }
}

