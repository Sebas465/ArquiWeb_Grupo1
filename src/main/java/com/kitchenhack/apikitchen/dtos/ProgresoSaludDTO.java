package com.kitchenhack.apikitchen.dtos;

import java.time.LocalDate;

public class ProgresoSaludDTO {

    private Integer id;
    private Integer idUsuario;
    private LocalDate fecha;
    private Double pesoKg;
    private Integer tallaCm;
    private Double imc;
    private String alergias;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Double getPesoKg() { return pesoKg; }
    public void setPesoKg(Double pesoKg) { this.pesoKg = pesoKg; }

    public Integer getTallaCm() { return tallaCm; }
    public void setTallaCm(Integer tallaCm) { this.tallaCm = tallaCm; }

    public Double getImc() { return imc; }
    public void setImc(Double imc) { this.imc = imc; }

    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }
}
