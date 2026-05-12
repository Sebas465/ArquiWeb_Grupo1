package com.kitchenhack.apikitchen.dtos;

// DTO para exponer y recibir datos de plan maestro (usa Integer para FK en lugar del objeto Usuario)
public class PlanMaestroDTO {

    private Integer id;
    private String titulo;
    private Long idAutor;   // ID del usuario autor, no el objeto completo
    private String tipoPlan;   // Valores válidos: 'alimenticio', 'ejercicio', 'hibrido'
    private Integer duracionDias;
    private String objetivo;

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

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
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
