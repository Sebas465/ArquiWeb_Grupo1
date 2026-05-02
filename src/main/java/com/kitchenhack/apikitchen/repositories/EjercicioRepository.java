package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Ejercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Acceso a la tabla ejercicio — hereda CRUD básico de JpaRepository
@Repository
public interface EjercicioRepository extends JpaRepository<Ejercicio, Integer> {
}
