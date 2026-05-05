package com.kitchenhack.apikitchen.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.kitchenhack.apikitchen.entities.Usuario;


@Repository
public interface IUserRepository extends JpaRepository<Usuario, Integer> {
    // Busca un usuario por su username (único)
    public Usuario findOneByUsername(String username);

    // Cuenta cuántos usuarios tienen ese username (útil para validaciones de existencia)
    @Query("select count(u.username) from Usuario u where u.username =:username")
    public int buscarUsername(@Param("username") String nombre);


}