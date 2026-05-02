package com.kitchenhack.apikitchen.dtos;

public class RecipeItemDTO {
    private Integer orden;
    private Boolean esPaso;
    private String contenido;
    private Double cantidad;
    private String nombreIngrediente;
    private String unidadMedida;

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
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

    public String getNombreIngrediente() {
        return nombreIngrediente;
    }

    public void setNombreIngrediente(String nombreIngrediente) {
        this.nombreIngrediente = nombreIngrediente;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
}
