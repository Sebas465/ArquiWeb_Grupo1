package com.kitchenhack.apikitchen.dtos;

/**
 * DTO para la petición de login.
 * Contiene las credenciales (username + password) que el cliente envía a
 * POST /login. La contraseña se transmite en claro por HTTPS y será verificada
 * por el AuthenticationManager.
 */
public class JwtRequestDTO  {
	private String username;
	private String password;

	public JwtRequestDTO() {
		super();
	}

	public JwtRequestDTO(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}