package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.SuscripcionPlanDTO;
import com.kitchenhack.apikitchen.entities.DiaPlanItem;
import com.kitchenhack.apikitchen.entities.PlanMaestro;
import com.kitchenhack.apikitchen.entities.ProgresoDiario;
import com.kitchenhack.apikitchen.entities.SuscripcionPlan;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.repositories.DiaPlanItemRepository;
import com.kitchenhack.apikitchen.repositories.ProgresoDiarioRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IPlanMaestroService;
import com.kitchenhack.apikitchen.servicesinterfaces.ISuscripcionPlanService;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// US-P4-S2-02, US-P4-S2-03, US-P4-S2-04: Gestión de suscripciones a planes
@RestController
@RequestMapping("/suscripciones")
public class SuscripcionPlanController {

    @Autowired
    private ISuscripcionPlanService suscripcionPlanService;

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private IPlanMaestroService planMaestroService;

    @Autowired
    private DiaPlanItemRepository diaPlanItemRepository;

    @Autowired
    private ProgresoDiarioRepository progresoDiarioRepository;

    // US-P4-S2-02 — Suscribir usuario a un plan maestro
    // POST /suscripciones → 201 con suscripción, 409 si ya existe, 404 si no existen usuario o plan
    @PostMapping
    public ResponseEntity<?> suscribirUsuario(@RequestBody SuscripcionPlanDTO dto) {

        if (dto.getIdUsuario() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El id_usuario es obligatorio");
        }
        Optional<Usuario> usuarioOpt = usuarioService.listId(dto.getIdUsuario());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        if (dto.getIdPlan() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El id_plan es obligatorio");
        }
        Optional<PlanMaestro> planOpt = planMaestroService.listId(dto.getIdPlan());
        if (planOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Plan no encontrado");
        }

        // Evitar duplicados: un usuario no puede tener el mismo plan activo dos veces
        if (suscripcionPlanService.existeSubscripcionActiva(dto.getIdUsuario(), dto.getIdPlan())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El usuario ya está suscrito a ese plan");
        }

        SuscripcionPlan suscripcion = new SuscripcionPlan(
                null,
                usuarioOpt.get(),
                planOpt.get(),
                LocalDate.now(),
                true
        );

        SuscripcionPlan guardada = suscripcionPlanService.insert(suscripcion);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(guardada));
    }

    // US-P4-S2-03 — Marcar un ítem del plan como completado
    // PATCH /suscripciones/{id}/item/{item_id}/completar → 200, 404 si no existe suscripción o ítem
    @PatchMapping("/{id}/item/{item_id}/completar")
    public ResponseEntity<?> completarItem(@PathVariable Integer id, @PathVariable Integer item_id) {

        // Verificar que la suscripción exista
        Optional<SuscripcionPlan> suscOpt = suscripcionPlanService.listId(id);
        if (suscOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Suscripción no encontrada");
        }

        // Verificar que el ítem del plan exista
        Optional<DiaPlanItem> itemOpt = diaPlanItemRepository.findById(item_id);
        if (itemOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ítem del plan no encontrado");
        }

        // Buscar registro existente o crear uno nuevo
        Optional<ProgresoDiario> progresoOpt =
                progresoDiarioRepository.findByIdSuscripcion_IdAndIdDiaPlanItem_Id(id, item_id);

        ProgresoDiario progreso = progresoOpt.orElse(
                new ProgresoDiario(null, suscOpt.get(), itemOpt.get(), false, null)
        );

        // Marcar como completado con la fecha actual
        progreso.setCompletado(true);
        progreso.setFechaRegistro(LocalDateTime.now());
        progresoDiarioRepository.save(progreso);

        return ResponseEntity.ok("Ítem completado");
    }

    // US-P4-S2-04 — Listar suscripciones activas de un usuario
    // GET /suscripciones/usuario/{id} → 200 con lista, 404 si no tiene suscripciones activas
    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> listarActivasPorUsuario(@PathVariable Long id) {
        List<SuscripcionPlan> lista = suscripcionPlanService.listActivasByUsuario(id);

        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sin suscripciones activas");
        }

        List<SuscripcionPlanDTO> listaDTO = lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaDTO);
    }

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
