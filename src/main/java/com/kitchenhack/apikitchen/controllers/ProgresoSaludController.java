package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.ProgresoSaludDTO;
import com.kitchenhack.apikitchen.entities.ProgresoSalud;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.servicesinterfaces.IProgresoSaludService;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/progreso-salud")
public class ProgresoSaludController {

    @Autowired
    private IProgresoSaludService progresoSaludService;

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<ProgresoSaludDTO>> listar() {
        ModelMapper m = new ModelMapper();
        List<ProgresoSaludDTO> lista = progresoSaludService.list()
                .stream().map(p -> toDTO(p, m)).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<ProgresoSalud> opt = progresoSaludService.listId(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(toDTO(opt.get(), new ModelMapper()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progreso de salud no encontrado");
        }
    }

    @PostMapping("/nuevo")
    public ResponseEntity<ProgresoSaludDTO> registrar(@RequestBody ProgresoSaludDTO dto) {
        ModelMapper m = new ModelMapper();
        // Validar usuario existe
        if (dto.getUsuarioId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Optional<Usuario> usuarioOpt = usuarioService.listId(dto.getUsuarioId());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // ModelMapper mapea los campos planos; la relación Usuario se asigna manualmente
        ProgresoSalud p = m.map(dto, ProgresoSalud.class);
        p.setId(null);
        p.setIdUsuario(usuarioOpt.get());
        p.setPesoKg(toBigDecimal(dto.getPesoKg()));
        p.setImc(toBigDecimal(dto.getImc()));

        ProgresoSalud saved = progresoSaludService.insert(p);
        ProgresoSaludDTO response = toDTO(saved, m);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody ProgresoSaludDTO dto) {
        Optional<ProgresoSalud> existente = progresoSaludService.listId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progreso de salud no encontrado");
        }

        ProgresoSalud p = existente.get();
        // validar usuario si viene
        if (dto.getUsuarioId() != null && !dto.getUsuarioId().equals(p.getIdUsuario().getId())) {
            Optional<Usuario> usuarioOpt = usuarioService.listId(dto.getUsuarioId());
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
            p.setIdUsuario(usuarioOpt.get());
        }
        if (dto.getFecha() != null) {
            p.setFecha(dto.getFecha());
        }
        if (dto.getPesoKg() != null) {
            p.setPesoKg(toBigDecimal(dto.getPesoKg()));
        }
        if (dto.getTallaCm() != null) {
            p.setTallaCm(dto.getTallaCm());
        }
        if (dto.getImc() != null) {
            p.setImc(toBigDecimal(dto.getImc()));
        }
        if (dto.getAlergias() != null) {
            p.setAlergias(dto.getAlergias());
        }
        progresoSaludService.update(p);
        return ResponseEntity.ok("Progreso de salud actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<ProgresoSalud> existente = progresoSaludService.listId(id);
        if (existente.isPresent()) {
            progresoSaludService.delete(id);
            return ResponseEntity.ok("Progreso de salud eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progreso de salud no encontrado");
        }
    }

    private BigDecimal toBigDecimal(Double value) {
        return value == null ? null : BigDecimal.valueOf(value);
    }

    // US-P4-S2-03 — Listar mediciones de un usuario filtradas por rango de fechas
    // GET /progreso-salud/usuario/{id}?inicio=2026-01-01&fin=2026-04-30
    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> listarPorUsuarioYRango(
            @PathVariable Long id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fin) {

        List<ProgresoSalud> lista = progresoSaludService.listByUsuarioAndRango(id, inicio, fin);

        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sin registros en ese periodo");
        }

        // Convertir cada entidad a DTO para la respuesta
        ModelMapper m = new ModelMapper();
        List<ProgresoSaludDTO> listaDTO = lista.stream()
                .map(p -> toDTO(p, m))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaDTO);
    }

    private ProgresoSaludDTO toDTO(ProgresoSalud p, ModelMapper m) {
        ProgresoSaludDTO dto = m.map(p, ProgresoSaludDTO.class);
        dto.setUsuarioId(p.getIdUsuario() != null ? p.getIdUsuario().getId() : null);
        return dto;
    }
}

