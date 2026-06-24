package com.kitchenhack.apikitchen.dtos;

public class EtiquetaDTO {

    private Integer id;
    private String nombre;
    private String grupo;

    public EtiquetaDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }
}
