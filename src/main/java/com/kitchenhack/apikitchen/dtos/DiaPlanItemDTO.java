package com.kitchenhack.apikitchen.dtos;

public class DiaPlanItemDTO {
    private Integer id;
    private Integer idPlan;
    private Integer numDia;
    private Integer idReceta;
    private Integer idEjercicio;
    private String momento;
    private Integer orden;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(Integer idPlan) {
        this.idPlan = idPlan;
    }

    public Integer getNumDia() {
        return numDia;
    }

    public void setNumDia(Integer numDia) {
        this.numDia = numDia;
    }

    public Integer getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Integer idReceta) {
        this.idReceta = idReceta;
    }

    public Integer getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public String getMomento() {
        return momento;
    }

    public void setMomento(String momento) {
        this.momento = momento;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }
}