package com.kitchenhack.apikitchen.dtos;

public class IngredienteDTO {

    private Integer id;
    private String nombre;
    private String unidadMedida;
    private Integer idEtiqueta;
    private Double calorias100;
    private Double proteinas100;
    private Double carbos100;
    private Double grasas100;

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

    public Integer getIdEtiqueta() {
        return idEtiqueta;
    }

    public void setIdEtiqueta(Integer idEtiqueta) {
        this.idEtiqueta = idEtiqueta;
    }

    public Double getCalorias100() {
        return calorias100;
    }

    public void setCalorias100(Double calorias100) {
        this.calorias100 = calorias100;
    }

    public Double getProteinas100() {
        return proteinas100;
    }

    public void setProteinas100(Double proteinas100) {
        this.proteinas100 = proteinas100;
    }

    public Double getCarbos100() {
        return carbos100;
    }

    public void setCarbos100(Double carbos100) {
        this.carbos100 = carbos100;
    }

    public Double getGrasas100() {
        return grasas100;
    }

    public void setGrasas100(Double grasas100) {
        this.grasas100 = grasas100;
    }
}

