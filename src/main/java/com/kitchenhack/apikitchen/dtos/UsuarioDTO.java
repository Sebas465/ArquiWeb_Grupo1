package com.kitchenhack.apikitchen.dtos;

/**
 * DTO usado en las APIs para representar datos de Usuario.
 *
 * Nota: el campo `contrasenaHash` se utiliza como transporte de la contraseña
 * desde el cliente en operaciones de registro/actualización. Nunca devolverlo
 * en respuestas (se limpia a null antes de enviar).
 */
public class UsuarioDTO {

    private Long id;
    private String username;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasenaHash;
    private Integer idRol;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }
}

