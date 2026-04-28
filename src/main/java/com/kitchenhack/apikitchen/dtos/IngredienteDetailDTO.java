package com.kitchenhack.apikitchen.dtos;

import java.math.BigDecimal;
import java.util.List;

public class IngredienteDetailDTO {

    private Long id;
    private String nombre;
    private BigDecimal totalCalories;
    private BigDecimal proteinGrams;
    private BigDecimal carbsGrams;
    private BigDecimal fatGrams;
    private List<String> alergenos;
    private Boolean alerta;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public BigDecimal getTotalCalories() { return totalCalories; }
    public void setTotalCalories(BigDecimal totalCalories) { this.totalCalories = totalCalories; }

    public BigDecimal getProteinGrams() { return proteinGrams; }
    public void setProteinGrams(BigDecimal proteinGrams) { this.proteinGrams = proteinGrams; }

    public BigDecimal getCarbsGrams() { return carbsGrams; }
    public void setCarbsGrams(BigDecimal carbsGrams) { this.carbsGrams = carbsGrams; }

    public BigDecimal getFatGrams() { return fatGrams; }
    public void setFatGrams(BigDecimal fatGrams) { this.fatGrams = fatGrams; }

    public List<String> getAlergenos() { return alergenos; }
    public void setAlergenos(List<String> alergenos) { this.alergenos = alergenos; }

    public Boolean getAlerta() { return alerta; }
    public void setAlerta(Boolean alerta) { this.alerta = alerta; }
}

