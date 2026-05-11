package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.SuscripcionPlanDTO;
import com.kitchenhack.apikitchen.entities.PlanMaestro;
import com.kitchenhack.apikitchen.entities.SuscripcionPlan;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.servicesinterfaces.IPlanMaestroService;
import com.kitchenhack.apikitchen.servicesinterfaces.ISuscripcionPlanService;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

// US-P4-S2-02: Suscribir un usuario a un plan maestro
@RestController
@RequestMapping("/suscripciones")
public class SuscripcionPlanController {

    @Autowired
    private ISuscripcionPlanService suscripcionPlanService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IPlanMaestroService planMaestroService;

    // US-P4-S2-02 — Suscribir usuario a un plan maestro
    // POST /suscripciones/nuevo → 201 con suscripción, 409 si ya existe, 404 si no existen usuario o plan
    @PostMapping("/nuevo")
    public ResponseEntity<?> suscribirUsuario(@RequestBody SuscripcionPlanDTO dto) {

        // Verificar que el usuario exista
        if (dto.getIdUsuario() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El id_usuario es obligatorio");
        }
        Optional<Usuario> usuarioOpt = usuarioService.listId(dto.getIdUsuario());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        // Verificar que el plan exista
        if (dto.getIdPlan() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El id_plan es obligatorio");
        }
        Optional<PlanMaestro> planOpt = planMaestroService.listId(dto.getIdPlan());
        if (planOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan no encontrado");
        }

        // Verificar que el usuario no tenga ya ese plan activo — evita duplicados
        if (suscripcionPlanService.existeSubscripcionActiva(dto.getIdUsuario(), dto.getIdPlan())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El usuario ya está suscrito a ese plan");
        }

        // Crear la suscripción con activo=TRUE y fecha de hoy
        SuscripcionPlan suscripcion = new SuscripcionPlan(
                null,
                usuarioOpt.get(),
                planOpt.get(),
                LocalDate.now(),
                true
        );

        SuscripcionPlan guardada = suscripcionPlanService.insert(suscripcion);

        // Construir el DTO de respuesta manualmente (FKs como IDs simples)
        SuscripcionPlanDTO respuesta = toDTO(guardada);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    // Convierte la entidad a DTO extrayendo solo los IDs de las relaciones
    private SuscripcionPlanDTO toDTO(SuscripcionPlan s) {
        SuscripcionPlanDTO dto = new SuscripcionPlanDTO();
        dto.setId(s.getId());
        dto.setIdUsuario(s.getIdUsuario() != null ? s.getIdUsuario().getId() : null);
        dto.setIdPlan(s.getIdPlan() != null ? s.getIdPlan().getId() : null);
        dto.setFechaInicio(s.getFechaInicio());
        dto.setActivo(s.getActivo());
        return dto;
    }
}
