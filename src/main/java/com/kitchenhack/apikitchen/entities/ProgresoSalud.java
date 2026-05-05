package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "progreso_salud")
public class ProgresoSalud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario idUsuario;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(name = "peso_kg")
    private BigDecimal pesoKg;

    @Column(name = "talla_cm")
    private Integer tallaCm;

    @Column(name = "imc")
    private BigDecimal imc;

    @Column(columnDefinition = "TEXT")
    private String alergias;

    // Constructor sin-argumentos requerido por JPA
    public ProgresoSalud() {
    }

    public ProgresoSalud(Integer id, Usuario idUsuario, LocalDate fecha, BigDecimal pesoKg, Integer tallaCm, BigDecimal imc, String alergias) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.pesoKg = pesoKg;
        this.tallaCm = tallaCm;
        this.imc = imc;
        this.alergias = alergias;
    }

    public ProgresoSalud(Object o, Long usuarioId, String alergias) {
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

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getPesoKg() {
        return pesoKg != null ? pesoKg.doubleValue() : null;
    }

    public void setPesoKg(BigDecimal pesoKg) {
        this.pesoKg = pesoKg;
    }

    public Integer getTallaCm() {
        return tallaCm;
    }

    public void setTallaCm(Integer tallaCm) {
        this.tallaCm = tallaCm;
    }

    public Double getImc() {
        return imc != null ? imc.doubleValue() : null;
    }

    public void setImc(BigDecimal imc) {
        this.imc = imc;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public void setUsuarioId(Long usuarioId) {
    }
}

