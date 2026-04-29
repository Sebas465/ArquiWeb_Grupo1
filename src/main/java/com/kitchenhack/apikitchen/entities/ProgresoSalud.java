package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

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
    private Double pesoKg;

    @Column(name = "talla_cm")
    private Integer tallaCm;

    @Column(name = "imc")
    private Double imc;

    @Column(columnDefinition = "TEXT")
    private String alergias;

    // Constructor sin-argumentos requerido por JPA
    public ProgresoSalud() {
    }

    public ProgresoSalud(Integer id, Usuario idUsuario, LocalDate fecha, Double pesoKg, Integer tallaCm, Double imc, String alergias) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.pesoKg = pesoKg;
        this.tallaCm = tallaCm;
        this.imc = imc;
        this.alergias = alergias;
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

