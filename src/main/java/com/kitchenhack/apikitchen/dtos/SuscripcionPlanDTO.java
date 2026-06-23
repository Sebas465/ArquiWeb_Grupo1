package com.kitchenhack.apikitchen.dtos;

import java.time.LocalDate;

// DTO para suscribir un usuario a un plan maestro
public class SuscripcionPlanDTO {

    private Integer id;
    private Integer idUsuario;       // FK a usuario (Integer)
    private Integer idPlan;       // FK a plan_maestro (Integer)
    private LocalDate fechaInicio; // Se asigna automáticamente en el servidor
    private Boolean activo;        // Siempre TRUE al crear

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public Integer getIdPlan() { return idPlan; }
    public void setIdPlan(Integer idPlan) { this.idPlan = idPlan; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}