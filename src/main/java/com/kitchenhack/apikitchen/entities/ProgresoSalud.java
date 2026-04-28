package com.kitchenhack.apikitchen.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "progreso_salud")
public class ProgresoSalud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_usuario", nullable = false)
    private Integer usuarioId;

    // Comma-separated allergens or a JSON/text representation depending on DB
    @Column(columnDefinition = "TEXT")
    private String alergias;

    // Constructor sin-argumentos requerido por JPA
    public ProgresoSalud() {
    }

    public ProgresoSalud(Integer id, Integer usuarioId, String alergias) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.alergias = alergias;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getAlergias() {
        return alergias;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }
}

