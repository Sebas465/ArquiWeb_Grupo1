package com.kitchenhack.apikitchen.dtos;

// DTO para retornar el conteo de favoritos por receta (GET /recetas/populares)
public class RecetaPopularidadDTO {

    private Long idReceta;
    private Long cantidad; // Número de veces marcada como favorito

    public RecetaPopularidadDTO(Long idReceta, Long cantidad) {
        this.idReceta = idReceta;
        this.cantidad = cantidad;
    }

    public Long getIdReceta() { return idReceta; }
    public void setIdReceta(Long idReceta) { this.idReceta = idReceta; }

    public Long getCantidad() { return cantidad; }
    public void setCantidad(Long cantidad) { this.cantidad = cantidad; }
}
