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
    Optional<Ingrediente> listId(Long id);

    // Elimina un ingrediente por su identificador.
    void delete(Long id);

    // Lista ingredientes por tipo/etiqueta (filtro opcional en GET /ingredientes?tipo=1)
    List<Ingrediente> findByTipo(Integer idEtiqueta);

    // Busca ingredientes por nombre (búsqueda parcial, insensible a mayúsculas)
    List<Ingrediente> searchByNombre(String nombre);

    // Busca ingredientes por nombre y tipo/etiqueta simultáneamente
    List<Ingrediente> searchByNombreAndTipo(String nombre, Integer idEtiqueta);
}
