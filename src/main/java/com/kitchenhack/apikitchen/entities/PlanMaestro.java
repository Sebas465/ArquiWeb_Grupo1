package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "plan_maestro")
public class PlanMaestro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @ManyToOne
    @JoinColumn(name = "id_autor", nullable = false)
    private Usuario idAutor;

    @Column(name = "tipo_plan", nullable = false, length = 20)
    private String tipoPlan;

    @Column(name = "duracion_dias", nullable = false)
    private Integer duracionDias;

    @Column(nullable = false, length = 100)
    private String objetivo;

    public PlanMaestro() {
    }

    public PlanMaestro(Integer id, String titulo, Usuario idAutor, String tipoPlan, Integer duracionDias, String objetivo) {
        this.id = id;
        this.titulo = titulo;
        this.idAutor = idAutor;
        this.tipoPlan = tipoPlan;
        this.duracionDias = duracionDias;
        this.objetivo = objetivo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Usuario getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Usuario idAutor) {
        this.idAutor = idAutor;
    }

    public String getTipoPlan() {
        return tipoPlan;
    }

    public void setTipoPlan(String tipoPlan) {
        this.tipoPlan = tipoPlan;
    }

    public Integer getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(Integer duracionDias) {
        this.duracionDias = duracionDias;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }
}

