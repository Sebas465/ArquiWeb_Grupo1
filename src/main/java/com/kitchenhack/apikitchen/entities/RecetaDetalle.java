package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "receta_detalle")
public class RecetaDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_receta", nullable = false)
    private Recipe idReceta;

    @ManyToOne
    @JoinColumn(name = "id_ingrediente")
    private Ingrediente idIngrediente;

    @Column(name = "cantidad")
    private BigDecimal cantidad;

    @Column(name = "es_paso")
    private Boolean esPaso = false;

    @Column(nullable = false)
    private Integer orden;

    @Column(columnDefinition = "TEXT")
    private String contenido;

    public RecetaDetalle() {
    }

    public RecetaDetalle(Integer id, Recipe idReceta, Ingrediente idIngrediente, BigDecimal cantidad, Boolean esPaso, Integer orden, String contenido) {
        this.id = id;
        this.idReceta = idReceta;
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
        this.esPaso = esPaso;
        this.orden = orden;
        this.contenido = contenido;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Recipe getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Recipe idReceta) {
        this.idReceta = idReceta;
    }

    public Ingrediente getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Ingrediente idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public Double getCantidad() {
        return cantidad != null ? cantidad.doubleValue() : null;
    }

    public void setCantidad(BigDecimal cantidad) {
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

