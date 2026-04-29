package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "sistema_evento")
public class SistemaEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 30)
    private String tipo;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(name = "contenido", columnDefinition = "TEXT")
    private String contenido;

    @Column(name = "leido_guardado")
    private Boolean leidoGuardado = false;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    public SistemaEvento() {
    }

    public SistemaEvento(Integer id, Usuario usuario, String tipo, String titulo, String contenido, Boolean leidoGuardado, LocalDateTime fecha) {
        this.id = id;
        this.usuario = usuario;
        this.tipo = tipo;
        this.titulo = titulo;
        this.contenido = contenido;
        this.leidoGuardado = leidoGuardado;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Boolean getLeidoGuardado() {
        return leidoGuardado;
    }

    public void setLeidoGuardado(Boolean leidoGuardado) {
        this.leidoGuardado = leidoGuardado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

