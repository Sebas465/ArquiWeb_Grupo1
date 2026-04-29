package com.kitchenhack.apikitchen.entities;

import com.kitchenhack.apikitchen.entities.Usuario;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rol", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "rol"})})
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rol;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Usuario usuario;


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}