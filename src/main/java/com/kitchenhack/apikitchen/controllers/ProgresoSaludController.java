package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.ProgresoSaludDTO;
import com.kitchenhack.apikitchen.entities.ProgresoSalud;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.servicesinterfaces.IProgresoSaludService;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/progreso-salud")
@CrossOrigin(origins = "*")
public class ProgresoSaludController {

    @Autowired
    private IProgresoSaludService progresoSaludService;

    @Autowired
    private IUsuarioService usuarioService;

    // ---------- Helpers ----------

    private ProgresoSaludDTO toDTO(ProgresoSalud p) {
        ProgresoSaludDTO dto = new ProgresoSaludDTO();
        dto.setId(p.getId());
        if (p.getIdUsuario() != null) {
            dto.setIdUsuario(p.getIdUsuario().getId());
        }
        dto.setFecha(p.getFecha());
        dto.setPesoKg(p.getPesoKg());
        dto.setTallaCm(p.getTallaCm());
        dto.setImc(p.getImc());
        dto.setAlergias(p.getAlergias());
        return dto;
    }

    private double calcularImc(double pesoKg, int tallaCm) {
        double tallaM = tallaCm / 100.0;
        return Math.round((pesoKg / (tallaM * tallaM)) * 100.0) / 100.0;
    }

    // ---------- GET /progreso-salud/usuario/{id}  (US-P3-02) ----------

    @GetMapping("/usuario/{id}")
    public ResponseEntity<?> listarPorUsuario(@PathVariable Integer id) {
        Optional<Usuario> usuarioOpt = usuarioService.listId(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sin registros de salud");
        }

        List<ProgresoSalud> lista = progresoSaludService.listByUsuario(usuarioOpt.get());
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sin registros de salud");
        }

        List<ProgresoSaludDTO> resultado = lista.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resultado);
    }

    // ---------- GET /progreso-salud ----------

    @GetMapping
    public ResponseEntity<List<ProgresoSaludDTO>> listar() {
        List<ProgresoSaludDTO> lista = progresoSaludService.list()
                .stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    // ---------- GET /progreso-salud/{id} ----------

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<ProgresoSalud> opt = progresoSaludService.listId(id);
        if (opt.isPresent()) {
            return ResponseEntity.ok(toDTO(opt.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progreso de salud no encontrado");
    }

    // ---------- POST /progreso-salud  (US-P3-01) ----------

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody ProgresoSaludDTO dto) {
        // Validar que id_usuario venga en el body
        if (dto.getIdUsuario() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("id_usuario es requerido");
        }

        // Criterio BDD: si el usuario no existe -> 404
        Optional<Usuario> usuarioOpt = usuarioService.listId(dto.getIdUsuario());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        // Construir entidad
        ProgresoSalud p = new ProgresoSalud();
        p.setIdUsuario(usuarioOpt.get());
        p.setFecha(dto.getFecha() != null ? dto.getFecha() : LocalDate.now());
        p.setPesoKg(dto.getPesoKg());
        p.setTallaCm(dto.getTallaCm());

        // Calcular IMC automaticamente si vienen peso y talla
        if (dto.getPesoKg() != null && dto.getTallaCm() != null && dto.getTallaCm() > 0) {
            p.setImc(calcularImc(dto.getPesoKg(), dto.getTallaCm()));
        } else {
            p.setImc(dto.getImc());
        }

        p.setAlergias(dto.getAlergias());

        ProgresoSalud saved = progresoSaludService.insert(p);

        // Criterio BDD: retorna 201 + objeto creado
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(saved));
    }

    // ---------- PUT /progreso-salud/{id} ----------

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody ProgresoSaludDTO dto) {
        Optional<ProgresoSalud> existente = progresoSaludService.listId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progreso de salud no encontrado");
        }

        ProgresoSalud p = existente.get();

        if (dto.getIdUsuario() != null) {
            Optional<Usuario> usuarioOpt = usuarioService.listId(dto.getIdUsuario());
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
            p.setIdUsuario(usuarioOpt.get());
        }
        if (dto.getFecha() != null) p.setFecha(dto.getFecha());
        if (dto.getPesoKg() != null) p.setPesoKg(dto.getPesoKg());
        if (dto.getTallaCm() != null) p.setTallaCm(dto.getTallaCm());

        // Recalcular IMC si hay nuevos valores
        if (p.getPesoKg() != null && p.getTallaCm() != null && p.getTallaCm() > 0) {
            p.setImc(calcularImc(p.getPesoKg(), p.getTallaCm()));
        } else if (dto.getImc() != null) {
            p.setImc(dto.getImc());
        }

        if (dto.getAlergias() != null) p.setAlergias(dto.getAlergias());

        ProgresoSalud updated = progresoSaludService.update(p);
        return ResponseEntity.ok(toDTO(updated));
    }

    // ---------- DELETE /progreso-salud/{id} ----------

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<ProgresoSalud> existente = progresoSaludService.listId(id);
        if (existente.isPresent()) {
            progresoSaludService.delete(id);
            return ResponseEntity.ok("Progreso de salud eliminado correctamente");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Progreso de salud no encontrado");
    }
}
