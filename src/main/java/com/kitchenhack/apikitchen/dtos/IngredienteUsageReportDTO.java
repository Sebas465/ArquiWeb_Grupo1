package com.kitchenhack.apikitchen.dtos;

public class IngredienteUsageReportDTO {
    private String nombreIngrediente;
    private Integer vecesUsado;

    // Getters y Setters
    public String getNombreIngrediente() { return nombreIngrediente; }
    public void setNombreIngrediente(String nombreIngrediente) { this.nombreIngrediente = nombreIngrediente; }
    public Integer getVecesUsado() { return vecesUsado; }
    public void setVecesUsado(Integer vecesUsado) { this.vecesUsado = vecesUsado; }
}