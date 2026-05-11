package com.kitchenhack.apikitchen.dtos;

// DTO para agregar o retornar un ítem de un día del plan
public class DiaPlanItemDTO {

    private Integer id;
    private Integer idPlan;      // FK a plan_maestro (Integer)
    private Integer numDia;      // Número del día dentro del plan
    private Long idReceta;       // FK a receta (Long) — opcional, uno de los dos debe estar
    private Integer idEjercicio; // FK a ejercicio (Integer) — opcional
    private String momento;      // 'desayuno', 'entrenamiento', 'cena', etc.
    private Integer orden;       // Orden dentro del día

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdPlan() { return idPlan; }
    public void setIdPlan(Integer idPlan) { this.idPlan = idPlan; }

    public Integer getNumDia() { return numDia; }
    public void setNumDia(Integer numDia) { this.numDia = numDia; }

    public Long getIdReceta() { return idReceta; }
    public void setIdReceta(Long idReceta) { this.idReceta = idReceta; }

    public Integer getIdEjercicio() { return idEjercicio; }
    public void setIdEjercicio(Integer idEjercicio) { this.idEjercicio = idEjercicio; }

    public String getMomento() { return momento; }
    public void setMomento(String momento) { this.momento = momento; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
}
