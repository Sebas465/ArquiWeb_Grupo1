package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "contacto_profesional")
public class ContactoProfesional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_profesional", nullable = false)
    private Usuario idProfesional;

    @Column(nullable = false, length = 20)
    private String estado = "pendiente";

    public ContactoProfesional() {
    }

    public ContactoProfesional(Integer id, Usuario idUsuario, Usuario idProfesional, String estado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idProfesional = idProfesional;
        this.estado = estado;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario getIdProfesional() {
        return idProfesional;
    }

    public void setIdProfesional(Usuario idProfesional) {
        this.idProfesional = idProfesional;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

