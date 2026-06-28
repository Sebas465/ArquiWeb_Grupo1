package com.kitchenhack.apikitchen.dtos;

import com.kitchenhack.apikitchen.entities.Etiqueta;

import java.math.BigDecimal;

public class IngredienteDTO {
    private Integer id;
    private String nombre;
    private String unidadMedida;
    private Etiqueta idEtiqueta;
    private BigDecimal calorias100;
    private BigDecimal proteinas100;
    private BigDecimal carbos100;
    private BigDecimal grasas100;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public BigDecimal getCalorias100() {
        return calorias100;
    }

    public void setCalorias100(BigDecimal calorias100) {
        this.calorias100 = calorias100;
    }

    public BigDecimal getProteinas100() {
        return proteinas100;
    }

    public void setProteinas100(BigDecimal proteinas100) {
        this.proteinas100 = proteinas100;
    }

    public BigDecimal getCarbos100() {
        return carbos100;
    }

    public void setCarbos100(BigDecimal carbos100) {
        this.carbos100 = carbos100;
    }

    public BigDecimal getGrasas100() {
        return grasas100;
    }

    public void setGrasas100(BigDecimal grasas100) {
        this.grasas100 = grasas100;
    }
}

