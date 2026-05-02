package com.kitchenhack.apikitchen.dtos;

// DTO para exponer y recibir datos de ejercicio en la API (sin exponer la entidad directamente)
public class EjercicioDTO {

    private Integer id;
    private String nombre;
    private String grupoMuscular;
    private Integer duracionMin;
    private Double metValor; // Equivalente energético (MET)

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
        return metValor;
    }

    public void setMetValor(Double metValor) {
        this.metValor = metValor;
    }
}
