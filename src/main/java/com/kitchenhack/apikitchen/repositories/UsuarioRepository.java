package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	/**
	 * Búsqueda case-insensitive por email.
	 * @param email email a buscar
	 * @return Optional con Usuario si existe
	 */
	@Query("select u from Usuario u where lower(u.email) = lower(:email)")
	Optional<Usuario> findByEmailJPQL(@Param("email") String email);

	/**
	 * Cuenta ocurrencias de username (ejemplo de consulta custom). Devuelve
	 * el número de registros con el username dado.
	 */
	@Query("select count(u.username) from Usuario u where u.username =:username")
	public int buscarUsername(@Param("username") String nombre);

	/** Busca usuario por username (exact match). */
	Optional<Usuario> findByUsername(String username);

	/** Comprueba existencia por email. */
	boolean existsByEmail(String email);

	/** Comprueba existencia por username. */
	boolean existsByUsername(String username);

	/** Variante que retorna la entidad (puede lanzar excepción si no existe). */
	public Usuario findOneByUsername(String username);

}
