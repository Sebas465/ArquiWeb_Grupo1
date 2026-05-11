package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.RecetaPopularidadDTO;
import com.kitchenhack.apikitchen.repositories.InteraccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// US-P3-S2-04: Contar favoritos por receta para identificar las más populares
@RestController
@RequestMapping("/recetas")
public class RecetaPopularidadController {

    @Autowired
    private InteraccionRepository interaccionRepository;

    // US-P3-S2-04 — Listar recetas ordenadas por número de favoritos
    // GET /recetas/populares → 200 con lista ordenada DESC, 404 si no hay favoritos
    @GetMapping("/populares")
    public ResponseEntity<?> listarPopulares() {

        // Query COUNT + GROUP BY sobre interacciones de tipo 'favorito'
        List<Object[]> resultados = interaccionRepository.countFavoritosByReceta();

        if (resultados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sin datos de popularidad");
        }

        // Convertir cada fila [idReceta, count] al DTO de respuesta
        List<RecetaPopularidadDTO> lista = resultados.stream()
                .map(row -> new RecetaPopularidadDTO((Long) row[0], (Long) row[1]))
                .collect(Collectors.toList());

        return ResponseEntity.ok(lista);
    }
}
