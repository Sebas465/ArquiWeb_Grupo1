package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.EtiquetaDTO;
import com.kitchenhack.apikitchen.entities.Etiqueta;
import com.kitchenhack.apikitchen.servicesinterfaces.IEtiquetaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/etiquetas")
public class EtiquetaController {

    @Autowired
    private IEtiquetaService etiquetaService;

    @GetMapping
    public ResponseEntity<?> listarEtiquetas() {
        List<Etiqueta> lista = etiquetaService.list();
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay etiquetas registradas");
        }
        ModelMapper m = new ModelMapper();
        List<EtiquetaDTO> listaDTO = lista.stream()
                .map(e -> m.map(e, EtiquetaDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> registrarEtiqueta(@RequestBody EtiquetaDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre es obligatorio");
        }
        if (dto.getGrupo() == null || dto.getGrupo().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El grupo es obligatorio");
        }
        Etiqueta etiqueta = new Etiqueta(null, dto.getNombre(), dto.getGrupo());
        Etiqueta guardada = etiquetaService.insert(etiqueta);
        ModelMapper m = new ModelMapper();
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "mensaje", "Etiqueta registrada",
                "etiqueta", m.map(guardada, EtiquetaDTO.class)
        ));
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> buscarEtiqueta(@PathVariable Integer id) {
        Optional<Etiqueta> opt = etiquetaService.listId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Etiqueta no encontrada");
        }
        ModelMapper m = new ModelMapper();
        return ResponseEntity.ok(m.map(opt.get(), EtiquetaDTO.class));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarEtiqueta(@PathVariable Integer id, @RequestBody EtiquetaDTO dto) {
        Optional<Etiqueta> opt = etiquetaService.listId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Etiqueta no encontrada");
        }
        Etiqueta etiqueta = opt.get();
        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            etiqueta.setNombre(dto.getNombre());
        }
        if (dto.getGrupo() != null && !dto.getGrupo().isBlank()) {
            etiqueta.setGrupo(dto.getGrupo());
        }
        etiquetaService.update(etiqueta);
        return ResponseEntity.ok("Etiqueta actualizada correctamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarEtiqueta(@PathVariable Integer id) {
        Optional<Etiqueta> opt = etiquetaService.listId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Etiqueta no encontrada");
        }
        etiquetaService.delete(id);
        return ResponseEntity.ok("Etiqueta eliminada correctamente");
    }
}
