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

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "categoria_receta")
    private Integer categoryId;

    @Column(name = "calorias_totales")
    private BigDecimal totalCalories;

    @Column(name = "proteinas_g")
    private BigDecimal proteinGrams;

    @Column(name = "carbohidratos_g")
    private BigDecimal carbsGrams;

    @Column(name = "grasas_g")
    private BigDecimal fatGrams;

    @Column(name = "tiempo_preparacion")
    private Integer prepTimeMinutes;

    @Column(name = "dificultad")
    private String difficulty;

    @Column(name = "calificacion_promedio")
    private BigDecimal averageRating;

    @Column(name = "publicada")
    private Boolean published;

    // Constructor sin-args requerido por JPA / ModelMapper
    public Recipe() {
    }

    public Recipe(Long id, String title, String description, String imageUrl, Integer categoryId, BigDecimal totalCalories, BigDecimal proteinGrams, BigDecimal carbsGrams, BigDecimal fatGrams, Integer prepTimeMinutes, String difficulty, BigDecimal averageRating, Boolean published) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.totalCalories = totalCalories;
        this.proteinGrams = proteinGrams;
        this.carbsGrams = carbsGrams;
        this.fatGrams = fatGrams;
        this.prepTimeMinutes = prepTimeMinutes;
        this.difficulty = difficulty;
        this.averageRating = averageRating;
        this.published = published;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public BigDecimal getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(BigDecimal totalCalories) {
        this.totalCalories = totalCalories;
    }

    public BigDecimal getProteinGrams() {
        return proteinGrams;
    }

    public void setProteinGrams(BigDecimal proteinGrams) {
        this.proteinGrams = proteinGrams;
    }

    public BigDecimal getCarbsGrams() {
        return carbsGrams;
    }

    public void setCarbsGrams(BigDecimal carbsGrams) {
        this.carbsGrams = carbsGrams;
    }

    public BigDecimal getFatGrams() {
        return fatGrams;
    }

    public void setFatGrams(BigDecimal fatGrams) {
        this.fatGrams = fatGrams;
    }

    public Integer getPrepTimeMinutes() {
        return prepTimeMinutes;
    }

    public void setPrepTimeMinutes(Integer prepTimeMinutes) {
        this.prepTimeMinutes = prepTimeMinutes;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public BigDecimal getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(BigDecimal averageRating) {
        this.averageRating = averageRating;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
}