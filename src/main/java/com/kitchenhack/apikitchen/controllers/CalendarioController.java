package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.CitaRequestDTO;
import com.kitchenhack.apikitchen.servicesinterfaces.ICalendarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calendario")
public class CalendarioController {

    @Autowired
    private ICalendarioService calendarioService;

    @PostMapping("/citas")
    public ResponseEntity<?> crearCita(@RequestBody CitaRequestDTO dto) {
        try {
            return ResponseEntity.ok(calendarioService.crearCita(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agendar la cita: " + e.getMessage());
        }
    }

    @GetMapping("/citas")
    public ResponseEntity<?> listarMisCitas(@RequestParam String username) {
        try {
            return ResponseEntity.ok(calendarioService.listarMisCitas(username));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las citas: " + e.getMessage());
        }
    }
}
