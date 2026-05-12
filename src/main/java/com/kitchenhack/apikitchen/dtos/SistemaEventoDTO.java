package com.kitchenhack.apikitchen.dtos;

import java.time.LocalDateTime;

public class SistemaEventoDTO {

    private Integer id;
    private Long idUsuario;
    private String tipo;
    private String titulo;
    private String contenido;
    private Boolean leidoGuardado;
    private LocalDateTime fecha;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
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

