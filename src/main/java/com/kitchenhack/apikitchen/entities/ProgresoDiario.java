package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "progreso_diario")
public class ProgresoDiario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_suscripcion", nullable = false)
    private SuscripcionPlan idSuscripcion;

    @ManyToOne
    @JoinColumn(name = "id_dia_plan_item", nullable = false)
    private DiaPlanItem idDiaPlanItem;

    @Column(nullable = false)
    private Boolean completado = false;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    public ProgresoDiario() {
    }

    public ProgresoDiario(Integer id, SuscripcionPlan idSuscripcion, DiaPlanItem idDiaPlanItem, Boolean completado, LocalDateTime fechaRegistro) {
        this.id = id;
        this.idSuscripcion = idSuscripcion;
        this.idDiaPlanItem = idDiaPlanItem;
        this.completado = completado;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SuscripcionPlan getIdSuscripcion() {
        return idSuscripcion;
    }

    public void setIdSuscripcion(SuscripcionPlan idSuscripcion) {
        this.idSuscripcion = idSuscripcion;
    }

    public DiaPlanItem getIdDiaPlanItem() {
        return idDiaPlanItem;
    }

    public void setIdDiaPlanItem(DiaPlanItem idDiaPlanItem) {
        this.idDiaPlanItem = idDiaPlanItem;
    }

    public Boolean getCompletado() {
        return completado;
    }

    public void setCompletado(Boolean completado) {
        this.completado = completado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}

