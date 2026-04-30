package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ejercicio")
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(name = "grupo_muscular", length = 100)
    private String grupoMuscular;

    @Column(name = "duracion_min")
    private Integer duracionMin;

    @Column(name = "met_valor")
    private BigDecimal metValor;

    public Ejercicio() {
    }

    public Ejercicio(Integer id, String nombre, String grupoMuscular, Integer duracionMin, BigDecimal metValor) {
        this.id = id;
        this.nombre = nombre;
        this.grupoMuscular = grupoMuscular;
        this.duracionMin = duracionMin;
        this.metValor = metValor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrupoMuscular() {
        return grupoMuscular;
    }

    public void setGrupoMuscular(String grupoMuscular) {
        this.grupoMuscular = grupoMuscular;
    }

    public Integer getDuracionMin() {
        return duracionMin;
    }

    public void setDuracionMin(Integer duracionMin) {
        this.duracionMin = duracionMin;
    }

    public Double getMetValor() {
        return metValor != null ? metValor.doubleValue() : null;
    }

    public void setMetValor(BigDecimal metValor) {
        this.metValor = metValor;
    }
}

