package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "dia_plan_item")
public class DiaPlanItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_plan", nullable = false)
    private PlanMaestro idPlan;

    @Column(name = "num_dia", nullable = false)
    private Integer numDia;

    @ManyToOne
    @JoinColumn(name = "id_receta")
    private Recipe idReceta;

    @ManyToOne
    @JoinColumn(name = "id_ejercicio")
    private Ejercicio idEjercicio;

    @Column(name = "momento", length = 30)
    private String momento;

    @Column(name = "orden")
    private Integer orden;

    public DiaPlanItem() {
    }

    public DiaPlanItem(Integer id, PlanMaestro idPlan, Integer numDia, Recipe idReceta, Ejercicio idEjercicio, String momento, Integer orden) {
        this.id = id;
        this.idPlan = idPlan;
        this.numDia = numDia;
        this.idReceta = idReceta;
        this.idEjercicio = idEjercicio;
        this.momento = momento;
        this.orden = orden;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlanMaestro getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(PlanMaestro idPlan) {
        this.idPlan = idPlan;
    }

    public Integer getNumDia() {
        return numDia;
    }

    public void setNumDia(Integer numDia) {
        this.numDia = numDia;
    }

    public Recipe getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Recipe idReceta) {
        this.idReceta = idReceta;
    }

    public Ejercicio getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(Ejercicio idEjercicio) {
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

