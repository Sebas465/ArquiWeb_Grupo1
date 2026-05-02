package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.EjercicioDTO;
import com.kitchenhack.apikitchen.entities.Ejercicio;
import com.kitchenhack.apikitchen.servicesinterfaces.IEjercicioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

// US-P4-01 y US-P4-02: Gestión del catálogo de ejercicios
@RestController
@RequestMapping("/ejercicios")
public class EjercicioController {

    @Autowired
    private IEjercicioService ejercicioService;

    // US-P4-02 — Listar todos los ejercicios del catálogo
    // GET /ejercicios → 200 con lista, o 404 si el catálogo está vacío
    @GetMapping
    public ResponseEntity<?> listarEjercicios() {
        List<Ejercicio> lista = ejercicioService.list();

        // Si el catálogo está vacío, se informa al cliente
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No hay ejercicios registrados");
        }

        // ModelMapper convierte cada entidad Ejercicio → EjercicioDTO
        ModelMapper m = new ModelMapper();
        List<EjercicioDTO> listaDTO = lista.stream()
                .map(e -> m.map(e, EjercicioDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaDTO);
    }

    // US-P4-01 — Registrar un nuevo ejercicio en el catálogo
    // POST /ejercicios/nuevo → 201 con el ejercicio creado, o 400 si falta el nombre
    @PostMapping("/nuevo")
    public ResponseEntity<?> registrarEjercicio(@RequestBody EjercicioDTO dto) {

        // El nombre es obligatorio según los criterios BDD
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("El nombre es obligatorio");
        }

        // Construir la entidad manualmente porque met_valor en BD es BigDecimal
        // y ModelMapper no convierte Double → BigDecimal en el setter de la entidad
        Ejercicio ejercicio = new Ejercicio(
                null,
                dto.getNombre(),
                dto.getGrupoMuscular(),
                dto.getDuracionMin(),
                dto.getMetValor() != null ? BigDecimal.valueOf(dto.getMetValor()) : null
        );

        Ejercicio guardado = ejercicioService.insert(ejercicio);

        // ModelMapper convierte la entidad guardada → DTO y se adjunta el mensaje de éxito
        ModelMapper m = new ModelMapper();
        EjercicioDTO respuesta = m.map(guardado, EjercicioDTO.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "mensaje", "Ejercicio agregado",
                "ejercicio", respuesta
        ));
    }

    // CRUD — Buscar ejercicio por ID
    // GET /ejercicios/detalle/{id} → 200 con el ejercicio, o 404 si no existe
    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> buscarEjercicio(@PathVariable Integer id) {
        Optional<Ejercicio> opt = ejercicioService.listId(id);

        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ejercicio no encontrado");
        }

        // ModelMapper convierte la entidad encontrada → DTO
        ModelMapper m = new ModelMapper();
        return ResponseEntity.ok(m.map(opt.get(), EjercicioDTO.class));
    }

    // CRUD — Actualizar un ejercicio existente
    // PUT /ejercicios/actualizar/{id} → 200 con mensaje, o 404 si no existe
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEjercicio(@PathVariable Integer id, @RequestBody EjercicioDTO dto) {
        Optional<Ejercicio> opt = ejercicioService.listId(id);

        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ejercicio no encontrado");
        }

        // Reutilizar la entidad existente y pisar solo los campos que vienen en el body
        Ejercicio ejercicio = opt.get();
        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            ejercicio.setNombre(dto.getNombre());
        }
        if (dto.getGrupoMuscular() != null) {
            ejercicio.setGrupoMuscular(dto.getGrupoMuscular());
        }
        if (dto.getDuracionMin() != null) {
            ejercicio.setDuracionMin(dto.getDuracionMin());
        }
        if (dto.getMetValor() != null) {
            // met_valor se almacena como BigDecimal en la entidad
            ejercicio.setMetValor(BigDecimal.valueOf(dto.getMetValor()));
        }

        ejercicioService.update(ejercicio);
        return ResponseEntity.ok("Ejercicio actualizado correctamente");
    }

    // CRUD — Eliminar un ejercicio del catálogo
    // DELETE /ejercicios/eliminar/{id} → 204 si se eliminó, o 404 si no existe
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEjercicio(@PathVariable Integer id) {
        Optional<Ejercicio> opt = ejercicioService.listId(id);

        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ejercicio no encontrado");
        }

        ejercicioService.delete(id);
        // 204 No Content: eliminación exitosa sin cuerpo en la respuesta
        return ResponseEntity.noContent().build();
    }
}
