package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.InteraccionDTO;
import com.kitchenhack.apikitchen.entities.Interaccion;
import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.repositories.RecipeRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IInteraccionService;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/interacciones")
@CrossOrigin(origins = "*")
public class InteraccionController {

    @Autowired
    private IInteraccionService interaccionService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private RecipeRepository recipeRepository;

    // ---------- Helper ----------

    private InteraccionDTO toDTO(Interaccion i) {
        InteraccionDTO dto = new InteraccionDTO();
        dto.setId(i.getId());
        if (i.getIdUsuario() != null) dto.setIdUsuario(i.getIdUsuario().getId());
        if (i.getIdReceta() != null)  dto.setIdReceta(i.getIdReceta().getId());
        dto.setTipo(i.getTipo());
        dto.setCalificacion(i.getCalificacion());
        dto.setComentario(i.getComentario());
        dto.setFecha(i.getFecha());
        return dto;
    }

    // ---------- POST /interacciones  (US-P3-03) ----------

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody InteraccionDTO dto) {

        // Validar campos requeridos
        if (dto.getIdUsuario() == null || dto.getIdReceta() == null || dto.getTipo() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("id_usuario, id_receta y tipo son requeridos");
        }

        // Validar tipo permitido
        String tipo = dto.getTipo().toLowerCase();
        if (!tipo.equals("favorito") && !tipo.equals("historial") && !tipo.equals("resena")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("tipo debe ser: favorito, historial o resena");
        }

        // Validar calificacion solo si es reseña
        if (tipo.equals("resena")) {
            if (dto.getCalificacion() == null
                    || dto.getCalificacion() < 1
                    || dto.getCalificacion() > 5) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Para tipo=resena se requiere calificacion entre 1 y 5");
            }
        }

        // Verificar que el usuario exista
        Optional<Usuario> usuarioOpt = usuarioService.listId(dto.getIdUsuario());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        // Verificar que la receta exista
        Optional<Recipe> recetaOpt = recipeRepository.findById(dto.getIdReceta());
        if (recetaOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Receta no encontrada");
        }

        // Criterio BDD: verificar duplicado usuario+receta+tipo -> 409
        Optional<Interaccion> duplicado = interaccionService.findDuplicado(
                usuarioOpt.get(), recetaOpt.get(), tipo);
        if (duplicado.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Esta interacción ya existe");
        }

        // Construir y guardar
        Interaccion interaccion = new Interaccion();
        interaccion.setIdUsuario(usuarioOpt.get());
        interaccion.setIdReceta(recetaOpt.get());
        interaccion.setTipo(tipo);
        interaccion.setCalificacion(dto.getCalificacion());
        interaccion.setComentario(dto.getComentario());
        interaccion.setFecha(LocalDateTime.now());

        Interaccion saved = interaccionService.insert(interaccion);

        // Criterio BDD: 201 + interacción creada
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    // ---------- GET /interacciones/usuario/{id}  (US-P3-04) ----------

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> listarPorUsuario(
            @PathVariable Integer id,
            @RequestParam(required = false) String tipo) {

        Optional<Usuario> usuarioOpt = usuarioService.listId(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sin interacciones registradas");
        }

        List<Interaccion> lista;
        if (tipo != null && !tipo.isBlank()) {
            // Filtrar por tipo (US-P3-04: ?tipo=favorito)
            lista = interaccionService.listByUsuarioAndTipo(usuarioOpt.get(), tipo.toLowerCase());
        } else {
            lista = interaccionService.listByUsuario(usuarioOpt.get());
        }

        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sin interacciones registradas");
        }

        List<InteraccionDTO> resultado = lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultado);
    }
}
