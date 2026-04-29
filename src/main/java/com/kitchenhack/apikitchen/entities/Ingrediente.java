package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "ingrediente")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 150)
    private String nombre;

    @Column(name = "unidad_medida", nullable = false, length = 20)
    private String unidadMedida;

    @ManyToOne
    @JoinColumn(name = "id_etiqueta")
    private Etiqueta idEtiqueta;

    @Column(name = "calorias_100")
    private BigDecimal calorias100;

    @Column(name = "proteinas_100")
    private BigDecimal proteinas100;

    @Column(name = "carbos_100")
    private BigDecimal carbos100;

    @Column(name = "grasas_100")
    private BigDecimal grasas100;

    // Constructor sin-args requerido por JPA/Hibernate
    public Ingrediente() {
    }

    public Ingrediente(Integer id, String nombre, String unidadMedida, Etiqueta idEtiqueta, BigDecimal calorias100, BigDecimal proteinas100, BigDecimal carbos100, BigDecimal grasas100) {
        this.id = id;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.idEtiqueta = idEtiqueta;
        this.calorias100 = calorias100;
        this.proteinas100 = proteinas100;
        this.carbos100 = carbos100;
        this.grasas100 = grasas100;
    }

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

    public Double getCalorias100() {
        return calorias100 != null ? calorias100.doubleValue() : null;
    }

    public void setCalorias100(BigDecimal calorias100) {
        this.calorias100 = calorias100;
    }

    public Double getProteinas100() {
        return proteinas100 != null ? proteinas100.doubleValue() : null;
    }

    public void setProteinas100(BigDecimal proteinas100) {
        this.proteinas100 = proteinas100;
    }

    public Double getCarbos100() {
        return carbos100 != null ? carbos100.doubleValue() : null;
    }

    public void setCarbos100(BigDecimal carbos100) {
        this.carbos100 = carbos100;
    }

    public Double getGrasas100() {
        return grasas100 != null ? grasas100.doubleValue() : null;
    }

    public void setGrasas100(BigDecimal grasas100) {
        this.grasas100 = grasas100;
    }
}

