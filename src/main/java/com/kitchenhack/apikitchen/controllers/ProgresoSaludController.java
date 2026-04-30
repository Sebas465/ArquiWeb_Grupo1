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

import java.math.BigDecimal;
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
                .stream().map(x -> m.map(x, ProgresoSaludDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<ProgresoSalud> opt = progresoSaludService.listId(id);
        if (opt.isPresent()) {
            ModelMapper m = new ModelMapper();
            return ResponseEntity.ok(m.map(opt.get(), ProgresoSaludDTO.class));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progreso de salud no encontrado");
        }
    }

    @PostMapping("/nuevo")
    public ResponseEntity<ProgresoSaludDTO> registrar(@RequestBody ProgresoSaludDTO dto) {
        // Validar usuario existe
        if (dto.getUsuarioId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Optional<Usuario> usuarioOpt = usuarioService.listId(dto.getUsuarioId());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Crear entidad con usuario entity (no Integer)
        ProgresoSalud p = new ProgresoSalud(
                null,
                usuarioOpt.get(),
                dto.getFecha(),
                toBigDecimal(dto.getPesoKg()),
                dto.getTallaCm(),
                toBigDecimal(dto.getImc()),
                dto.getAlergias());

        ProgresoSalud saved = progresoSaludService.insert(p);
        ModelMapper m = new ModelMapper();
        ProgresoSaludDTO response = m.map(saved, ProgresoSaludDTO.class);
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

    private ProgresoSaludDTO toDTO(ProgresoSalud p) {
        ProgresoSaludDTO dto = new ProgresoSaludDTO();
        dto.setId(p.getId());
        dto.setUsuarioId(p.getIdUsuario() != null ? p.getIdUsuario().getId() : null);
        dto.setFecha(p.getFecha());
        dto.setPesoKg(p.getPesoKg());
        dto.setTallaCm(p.getTallaCm());
        dto.setImc(p.getImc());
        dto.setAlergias(p.getAlergias());
        return dto;
    }
}

