package com.kitchenhack.apikitchen.dtos;

import com.kitchenhack.apikitchen.entities.Etiqueta;

public class IngredienteDTO {

    private String nombre;
    private String unidadMedida;
    private Etiqueta idEtiqueta;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Etiqueta getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(Etiqueta idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }
}

