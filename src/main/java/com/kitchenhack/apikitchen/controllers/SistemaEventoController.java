package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.SistemaEventoDTO;
import com.kitchenhack.apikitchen.entities.SistemaEvento;
import com.kitchenhack.apikitchen.servicesinterfaces.ISistemaEventoService;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
import com.kitchenhack.apikitchen.entities.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sistema-eventos")
@CrossOrigin(origins = "*")
public class SistemaEventoController {

    @Autowired
    private ISistemaEventoService sistemaEventoService;

    @Autowired
    private IUsuarioService usuarioService;

    // GET http://localhost:8080/sistema-eventos
    @GetMapping
    public ResponseEntity<List<SistemaEventoDTO>> listar() {
        List<SistemaEvento> list = sistemaEventoService.list();
        List<SistemaEventoDTO> dtos = list.stream().map(this::toDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET http://localhost:8080/sistema-eventos/1
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Optional<SistemaEvento> evento = sistemaEventoService.listId(id);
        if (evento.isPresent()) {
            SistemaEventoDTO dto = toDTO(evento.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento del sistema no encontrado");
        }
    }

    // POST http://localhost:8080/sistema-eventos
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody SistemaEventoDTO dto) {
        ModelMapper m = new ModelMapper();
        // Validar que venga idUsuario y que exista
        if (dto.getIdUsuario() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("idUsuario es requerido");
        }
        Optional<Usuario> usuarioOpt = usuarioService.listId(dto.getIdUsuario());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
        SistemaEvento evento = m.map(dto, SistemaEvento.class);
        // Asignar la entidad Usuario existente
        evento.setUsuario(usuarioOpt.get());
        SistemaEvento saved = sistemaEventoService.insert(evento);
        SistemaEventoDTO response = toDTO(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT http://localhost:8080/sistema-eventos/1
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                       @RequestBody SistemaEventoDTO dto) {
        Optional<SistemaEvento> existente = sistemaEventoService.listId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento del sistema no encontrado");
        }
        SistemaEvento evento = existente.get();
        // Actualizar campos permitidos
        evento.setTipo(dto.getTipo());
        evento.setTitulo(dto.getTitulo());
        evento.setContenido(dto.getContenido());
        evento.setLeidoGuardado(dto.getLeidoGuardado());
        evento.setFecha(dto.getFecha());
        // Actualizar usuario si viene: validar existencia
        if (dto.getIdUsuario() != null) {
            Optional<Usuario> usuarioOpt = usuarioService.listId(dto.getIdUsuario());
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
            evento.setUsuario(usuarioOpt.get());
        }
        sistemaEventoService.update(evento);
        return ResponseEntity.ok("Evento del sistema actualizado correctamente");
    }

    // DELETE http://localhost:8080/sistema-eventos/1
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<SistemaEvento> evento = sistemaEventoService.listId(id);
        if (evento.isPresent()) {
            sistemaEventoService.delete(id);
            return ResponseEntity.ok("Evento del sistema eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento del sistema no encontrado");
        }
    }

    private SistemaEventoDTO toDTO(SistemaEvento evento) {
        SistemaEventoDTO dto = new SistemaEventoDTO();
        dto.setId(evento.getId());
        dto.setIdUsuario(evento.getUsuario() != null ? evento.getUsuario().getId() : null);
        dto.setTipo(evento.getTipo());
        dto.setTitulo(evento.getTitulo());
        dto.setContenido(evento.getContenido());
        dto.setLeidoGuardado(evento.getLeidoGuardado());
        dto.setFecha(evento.getFecha());
        return dto;
    }
}

