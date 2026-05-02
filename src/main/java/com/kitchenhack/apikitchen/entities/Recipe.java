package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Usuario idAutor;

    @Column(name = "tiempo_min")
    private Integer prepTimeMinutes;

    @Column(name = "dificultad")
    private String difficulty;

    @Column(name = "publicada")
    private Boolean published;

    @Column(name = "ultima_act")
    private LocalDateTime ultimaActualizacion;

    //Sirve para RecipeController
    @OneToMany(mappedBy = "idReceta") // 'idReceta' es el nombre del campo en RecetaDetalle
    private List<RecetaDetalle> detalles;

    public List<RecetaDetalle> getDetalles() {
        return detalles;
    }

    public Recipe() {
    }

    public Recipe(Long id, String title, String description, Usuario idAutor, Integer prepTimeMinutes, String difficulty, Long categoryId, Double totalCalories, Double averageRating, Boolean published, LocalDateTime ultimaActualizacion) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.idAutor = idAutor;
        this.prepTimeMinutes = prepTimeMinutes;
        this.difficulty = difficulty;
        this.published = published;
        this.ultimaActualizacion = ultimaActualizacion;
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

    public Usuario getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Usuario idAutor) {
        this.idAutor = idAutor;
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

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public LocalDateTime getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(LocalDateTime ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }
}