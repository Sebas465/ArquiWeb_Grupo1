package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.RolDTO;
import com.kitchenhack.apikitchen.entities.Rol;
import com.kitchenhack.apikitchen.servicesinterfaces.IRolService;
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
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private IRolService rolService;

    @GetMapping
    public ResponseEntity<?> listarRoles() {
        List<Rol> lista = rolService.list();
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay roles registrados");
        }
        ModelMapper m = new ModelMapper();
        List<RolDTO> listaDTO = lista.stream()
                .map(r -> m.map(r, RolDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> registrarRol(@RequestBody RolDTO dto) {
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El nombre es obligatorio");
        }
        Rol rol = new Rol(null, dto.getNombre());
        Rol guardado = rolService.insert(rol);
        ModelMapper m = new ModelMapper();
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "mensaje", "Rol registrado",
                "rol", m.map(guardado, RolDTO.class)
        ));
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> buscarRol(@PathVariable Integer id) {
        Optional<Rol> opt = rolService.listId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }
        ModelMapper m = new ModelMapper();
        return ResponseEntity.ok(m.map(opt.get(), RolDTO.class));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarRol(@PathVariable Integer id, @RequestBody RolDTO dto) {
        Optional<Rol> opt = rolService.listId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }
        Rol rol = opt.get();
        if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
            rol.setNombre(dto.getNombre());
        }
        rolService.update(rol);
        return ResponseEntity.ok("Rol actualizado correctamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarRol(@PathVariable Integer id) {
        Optional<Rol> opt = rolService.listId(id);
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rol no encontrado");
        }
        rolService.delete(id);
        return ResponseEntity.ok("Rol eliminado correctamente");
    }
}
