package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Ingrediente;

import java.util.List;
import java.util.Optional;

public interface IIngredienteService {

    // Lista todos los ingredientes registrados.
    List<Ingrediente> list();

    // Guarda un nuevo ingrediente en la base de datos.
    Ingrediente insert(Ingrediente ingrediente);

    // Persiste cambios sobre un ingrediente existente.
    void update(Ingrediente ingrediente);

    // Busca un ingrediente por su identificador.
    Optional<Ingrediente> listId(Integer id);

    // Elimina un ingrediente por su identificador.
    void delete(Integer id);

    // Lista ingredientes por tipo (filtro opcional en GET /ingredientes?tipo=1)
    List<Ingrediente> findByTipo(Integer tipoIngredienteId);

    // US-P2-07
    boolean existePorId(int id);

    Optional<Ingrediente> listarPorId(int id);
}