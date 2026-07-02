package com.kitchenhack.apikitchen.dtos;

public class RecipeStatsDTO {
    private String dificultad;
    private Integer totalRecetas;
    private Double tiempoPromedio;

    public String getDificultad() { return dificultad; }
    public void setDificultad(String dificultad) { this.dificultad = dificultad; }
    public Integer getTotalRecetas() { return totalRecetas; }
    public void setTotalRecetas(Integer totalRecetas) { this.totalRecetas = totalRecetas; }
    public Double getTiempoPromedio() { return tiempoPromedio; }
    public void setTiempoPromedio(Double tiempoPromedio) { this.tiempoPromedio = tiempoPromedio; }
}