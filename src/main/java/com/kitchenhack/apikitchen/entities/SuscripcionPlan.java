package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "suscripcion_plan")
public class SuscripcionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_plan", nullable = false)
    private PlanMaestro idPlan;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private Boolean activo = true;

    public SuscripcionPlan() {
    }

    public SuscripcionPlan(Integer id, Usuario idUsuario, PlanMaestro idPlan, LocalDate fechaInicio, Boolean activo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idPlan = idPlan;
        this.fechaInicio = fechaInicio;
        this.activo = activo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public PlanMaestro getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(PlanMaestro idPlan) {
        this.idPlan = idPlan;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}

