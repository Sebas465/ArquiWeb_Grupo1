package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Ejercicio;

import java.util.List;
import java.util.Optional;

// Contrato de negocio para operaciones sobre ejercicios del catálogo
public interface IEjercicioService {

    // Retorna todos los ejercicios registrados en el catálogo
    List<Ejercicio> list();

    // Persiste un nuevo ejercicio en la base de datos
    Ejercicio insert(Ejercicio ejercicio);

    // Busca un ejercicio por su ID
    Optional<Ejercicio> listId(Integer id);

    // Actualiza los datos de un ejercicio existente
    void update(Ejercicio ejercicio);

    // Elimina un ejercicio por su ID
    void delete(Integer id);
}
