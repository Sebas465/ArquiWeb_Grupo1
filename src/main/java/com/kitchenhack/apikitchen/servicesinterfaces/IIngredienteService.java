package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Ingrediente;
import java.util.List;
import java.util.Optional;

public interface IIngredienteService {
    List<Ingrediente> list();
    Optional<Ingrediente> listId(Long id);
    Ingrediente insert(Ingrediente ingrediente);
    void update(Ingrediente ingrediente);
    void delete(Long id);

    // Métodos de búsqueda específicos
    List<Ingrediente> findByTipo(Integer tipo);
    List<Ingrediente> searchByNombre(String nombre);
    List<Ingrediente> searchByNombreAndTipo(String nombre, Integer tipo);
}