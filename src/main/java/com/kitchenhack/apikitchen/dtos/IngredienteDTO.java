package com.kitchenhack.apikitchen.dtos;

public class IngredienteDTO {

    private Long id;
    private String nombre;
    private Integer tipoIngredienteId;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getTipoIngredienteId() { return tipoIngredienteId; }
    public void setTipoIngredienteId(Integer tipoIngredienteId) { this.tipoIngredienteId = tipoIngredienteId; }
}

