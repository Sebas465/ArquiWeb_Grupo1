package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.ProgresoSaludDTO;
import com.kitchenhack.apikitchen.servicesinterfaces.IProgresoSaludService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progreso-salud")
@CrossOrigin(origins = "*")
public class ProgresoSaludController {

    @Autowired
    private IProgresoSaludService progresoSaludService;

    @GetMapping
    public ResponseEntity<List<ProgresoSaludDTO>> listar() {
        return ResponseEntity.ok(progresoSaludService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgresoSaludDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(progresoSaludService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ProgresoSaludDTO> crear(@RequestBody ProgresoSaludDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(progresoSaludService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgresoSaludDTO> actualizar(@PathVariable Integer id, @RequestBody ProgresoSaludDTO dto) {
        return ResponseEntity.ok(progresoSaludService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        progresoSaludService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

