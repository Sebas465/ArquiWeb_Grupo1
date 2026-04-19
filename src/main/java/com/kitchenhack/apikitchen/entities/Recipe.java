package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "receta")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false)
    private String title;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "calorias_totales")
    private BigDecimal totalCalories;

    @Column(name = "proteinas_g")
    private BigDecimal proteinGrams;

    @Column(name = "carbohidratos_g")
    private BigDecimal carbsGrams;

    @Column(name = "grasas_g")
    private BigDecimal fatGrams;

    @Column(name = "publicada")
    private Boolean published;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getTotalCalories() { return totalCalories; }
    public void setTotalCalories(BigDecimal totalCalories) { this.totalCalories = totalCalories; }

    public BigDecimal getProteinGrams() { return proteinGrams; }
    public void setProteinGrams(BigDecimal proteinGrams) { this.proteinGrams = proteinGrams; }

    public BigDecimal getCarbsGrams() { return carbsGrams; }
    public void setCarbsGrams(BigDecimal carbsGrams) { this.carbsGrams = carbsGrams; }

    public BigDecimal getFatGrams() { return fatGrams; }
    public void setFatGrams(BigDecimal fatGrams) { this.fatGrams = fatGrams; }

    public Boolean getPublished() { return published; }
    public void setPublished(Boolean published) { this.published = published; }
}