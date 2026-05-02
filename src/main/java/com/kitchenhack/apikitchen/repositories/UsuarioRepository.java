package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

}
