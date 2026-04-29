package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "perfil_profesional")
public class PerfilProfesional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario idUsuario;

    @Column(nullable = false, length = 100)
    private String especialidad;

    @Column(name = "numero_colegiatura", length = 100)
    private String numeroColegiatura;

    @Column(nullable = false)
    private Boolean verificado = false;

    public PerfilProfesional() {
    }

    public PerfilProfesional(Integer id, Usuario idUsuario, String especialidad, String numeroColegiatura, Boolean verificado) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.especialidad = especialidad;
        this.numeroColegiatura = numeroColegiatura;
        this.verificado = verificado;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getNumeroColegiatura() {
        return numeroColegiatura;
    }

    public void setNumeroColegiatura(String numeroColegiatura) {
        this.numeroColegiatura = numeroColegiatura;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }
}

