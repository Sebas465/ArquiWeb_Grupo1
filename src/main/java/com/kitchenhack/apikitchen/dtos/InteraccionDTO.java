package com.kitchenhack.apikitchen.dtos;

import java.time.LocalDateTime;

public class InteraccionDTO {

    private Integer id;
    private Integer idUsuario;
    private Long idReceta;
    private String tipo;
    private Integer calificacion;
    private String comentario;
    private LocalDateTime fecha;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Long getIdReceta() { return idReceta; }
    public void setIdReceta(Long idReceta) { this.idReceta = idReceta; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Integer getCalificacion() { return calificacion; }
    public void setCalificacion(Integer calificacion) { this.calificacion = calificacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
