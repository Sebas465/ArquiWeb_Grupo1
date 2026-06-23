package com.kitchenhack.apikitchen.dtos;

public class RecetaDetalleDTO {
    private Integer id;
    private Integer idReceta;
    private Integer idIngrediente;
    private Boolean esPaso;
    private String contenido;
    private Double cantidad;
    private Integer orden;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    public Integer getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Integer idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public Boolean getEsPaso() {
        return esPaso;
    }

    public void setEsPaso(Boolean esPaso) {
        this.esPaso = esPaso;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
}