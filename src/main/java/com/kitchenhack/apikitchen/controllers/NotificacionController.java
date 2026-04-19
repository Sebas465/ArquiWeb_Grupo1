package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.NotificacionDTO;
import com.kitchenhack.apikitchen.servicesinterfaces.INotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionController {

    @Autowired
    private INotificacionService notificacionService;

    // GET http://localhost:8080/notificaciones
    @GetMapping
    public ResponseEntity<List<NotificacionDTO>> listar() {
        return ResponseEntity.ok(notificacionService.listar());
    }

    // GET http://localhost:8080/notificaciones/1
    @GetMapping("/{id}")
    public ResponseEntity<NotificacionDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(notificacionService.buscarPorId(id));
    }

    // POST http://localhost:8080/notificaciones
    @PostMapping
    public ResponseEntity<NotificacionDTO> crear(@RequestBody NotificacionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificacionService.crear(dto));
    }

    // PUT http://localhost:8080/notificaciones/1
    @PutMapping("/{id}")
    public ResponseEntity<NotificacionDTO> actualizar(@PathVariable Integer id,
                                                       @RequestBody NotificacionDTO dto) {
        return ResponseEntity.ok(notificacionService.actualizar(id, dto));
    }

    // DELETE http://localhost:8080/notificaciones/1
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        notificacionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
