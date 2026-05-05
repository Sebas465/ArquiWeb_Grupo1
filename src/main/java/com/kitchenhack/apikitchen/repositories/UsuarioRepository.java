package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	@Query("select u from Usuario u where lower(u.email) = lower(:email)")
	Optional<Usuario> findByEmailJPQL(@Param("email") String email);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	// Busca un usuario por su username (para autenticación)
	Usuario findOneByUsername(String username);
}
