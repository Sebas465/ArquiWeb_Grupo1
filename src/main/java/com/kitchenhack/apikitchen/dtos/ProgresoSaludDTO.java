package com.kitchenhack.apikitchen.dtos;

import java.time.LocalDate;

public class ProgresoSaludDTO {

    private Integer id;
    private Long usuarioId;
    private LocalDate fecha;
    private Double pesoKg;
    private Integer tallaCm;
    private Double imc;
    private String alergias;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId != null ? usuarioId.longValue() : null;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getPesoKg() {
        return pesoKg;
    }

    public void setPesoKg(Double pesoKg) {
        this.pesoKg = pesoKg;
    }

    public Integer getTallaCm() {
        return tallaCm;
    }

    public void setTallaCm(Integer tallaCm) {
        this.tallaCm = tallaCm;
    }

    public Double getImc() {
        return imc;
    }

    public void setImc(Double imc) {
        this.imc = imc;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }
}

