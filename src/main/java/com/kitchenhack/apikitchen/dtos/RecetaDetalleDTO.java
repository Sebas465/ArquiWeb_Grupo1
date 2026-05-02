package com.kitchenhack.apikitchen.dtos;

public class RecetaDetalleDTO {
    private Integer idIngrediente; // Puede ser null si es paso
    private Double cantidad;       // Puede ser null si es paso
    private Boolean esPaso;
    private Integer orden;
    private String contenido;

    public Integer getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Integer idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Boolean getEsPaso() {
        return esPaso;
    }

    public void setEsPaso(Boolean esPaso) {
        this.esPaso = esPaso;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
