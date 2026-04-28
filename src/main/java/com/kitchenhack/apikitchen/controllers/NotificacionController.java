package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.NotificacionDTO;
import com.kitchenhack.apikitchen.entities.Notificacion;
import com.kitchenhack.apikitchen.servicesinterfaces.INotificacionService;
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
@RequestMapping("/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionController {

    @Autowired
    private INotificacionService notificacionService;

    @Autowired
    private IUsuarioService usuarioService;

    // GET http://localhost:8080/notificaciones
    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> listar() {
        ModelMapper m = new ModelMapper();
        List<Notificacion> list = notificacionService.list();
        List<NotificacionDTO> dtos = list.stream().map(n -> m.map(n, NotificacionDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // GET http://localhost:8080/notificaciones/1
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        ModelMapper m = new ModelMapper();
        Optional<Notificacion> n = notificacionService.listId(id);
        if (n.isPresent()) {
            NotificacionDTO dto = m.map(n.get(), NotificacionDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificacion no encontrada");
        }
    }

    // POST http://localhost:8080/notificaciones
    @PostMapping
    public ResponseEntity<NotificacionDTO> crear(@RequestBody NotificacionDTO dto) {
        ModelMapper m = new ModelMapper();
        // Validar que venga idUsuario y que exista (seguimos el patrón de UsuarioController: validaciones en controlador)
        if (dto.getIdUsuario() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        java.util.Optional<Usuario> usuarioOpt = usuarioService.listId(dto.getIdUsuario());
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Notificacion n = m.map(dto, Notificacion.class);
        // Asignar la entidad Usuario existente
        n.setUsuario(usuarioOpt.get());
        Notificacion saved = notificacionService.insert(n);
        NotificacionDTO response = m.map(saved, NotificacionDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT http://localhost:8080/notificaciones/1
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                                       @RequestBody NotificacionDTO dto) {
        Optional<Notificacion> existente = notificacionService.listId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificacion no encontrada");
        }
        Notificacion n = existente.get();
        // actualizar campos permitidos
        n.setTipo(dto.getTipo());
        n.setTitulo(dto.getTitulo());
        n.setCuerpo(dto.getCuerpo());
        n.setLeida(dto.getLeida());
        n.setFechaEnvio(dto.getFechaEnvio());
        // actualizar usuario si viene: validar existencia
        if (dto.getIdUsuario() != null) {
            java.util.Optional<Usuario> usuarioOpt = usuarioService.listId(dto.getIdUsuario());
            if (usuarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
            n.setUsuario(usuarioOpt.get());
        }
        notificacionService.update(n);
        return ResponseEntity.ok("Notificacion actualizada correctamente");
    }

    // DELETE http://localhost:8080/notificaciones/1
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        Optional<Notificacion> n = notificacionService.listId(id);
        if (n.isPresent()) {
            notificacionService.delete(id);
            return ResponseEntity.ok("Notificacion eliminada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Notificacion no encontrada");
        }
    }
}
