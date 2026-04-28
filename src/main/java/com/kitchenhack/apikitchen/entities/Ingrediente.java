package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "ingrediente")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(name = "tipo_ingrediente_id")
    private Integer tipoIngredienteId;

    // Constructor sin-args requerido por JPA/Hibernate
    public Ingrediente() {
    }

    public Ingrediente(Long id, String nombre, Integer tipoIngredienteId) {
        this.id = id;
        this.nombre = nombre;
        this.tipoIngredienteId = tipoIngredienteId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTipoIngredienteId() {
        return tipoIngredienteId;
    }

    public void setTipoIngredienteId(Integer tipoIngredienteId) {
        this.tipoIngredienteId = tipoIngredienteId;
    }
}

