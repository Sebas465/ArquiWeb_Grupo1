package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interaccion")
public class Interaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_receta", nullable = false)
    private Recipe idReceta;

    @Column(nullable = false, length = 20)
    private String tipo;

    @Column(name = "calificacion")
    private Integer calificacion;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    public Interaccion() {
    }

    public Interaccion(Integer id, Usuario idUsuario, Recipe idReceta, String tipo, Integer calificacion, String comentario, LocalDateTime fecha) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idReceta = idReceta;
        this.tipo = tipo;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fecha = fecha;
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

    public Recipe getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Recipe idReceta) {
        this.idReceta = idReceta;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

