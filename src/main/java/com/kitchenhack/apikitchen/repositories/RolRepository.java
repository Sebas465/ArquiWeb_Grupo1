package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
}
