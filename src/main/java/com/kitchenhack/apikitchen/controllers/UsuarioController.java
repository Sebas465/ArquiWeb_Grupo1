package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.UsuarioDTO;
import com.kitchenhack.apikitchen.entities.Rol;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        // Cada entidad se convierte a DTO para no devolver directamente la entidad JPA.
        List<UsuarioDTO> listaUsuarios = usuarioService.list()
                .stream().map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        java.util.Optional<Usuario> usuario = usuarioService.listId(id);

        if (usuario.isPresent()) {
            UsuarioDTO dto = toDTO(usuario.get());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody UsuarioDTO dto) {
        if (dto.getContrasenaHash() == null || dto.getContrasenaHash().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("contrasenaHash es requerido");
        }

        Usuario u = new Usuario();
        u.setUsername(dto.getUsername());
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setEmail(dto.getEmail());
        u.setContrasenaHash(dto.getContrasenaHash());

        Rol rol = new Rol();
        rol.setId(dto.getIdRol() != null ? dto.getIdRol() : 1);
        u.setIdRol(rol);
        LocalDateTime now = LocalDateTime.now();
        u.setFechaRegistro(now);
        u.setUltimaActividad(now);

        Usuario saved = usuarioService.insert(u);
        UsuarioDTO responseDTO = toDTO(saved);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody UsuarioDTO dto) {
        java.util.Optional<Usuario> existente = usuarioService.listId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        // Reutilizar la entidad encontrada y modificar solo los campos permitidos
        Usuario u = existente.get();
        u.setUsername(dto.getUsername());
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setEmail(dto.getEmail());
        if (dto.getContrasenaHash() != null && !dto.getContrasenaHash().isBlank()) {
            u.setContrasenaHash(dto.getContrasenaHash());
        }
        if (dto.getIdRol() != null) {
            Rol rol = new Rol();
            rol.setId(dto.getIdRol());
            u.setIdRol(rol);
        }
        u.setUltimaActividad(LocalDateTime.now());

        usuarioService.update(u);

        return ResponseEntity.ok("Usuario actualizado correctamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        java.util.Optional<Usuario> usuario = usuarioService.listId(id);

        if (usuario.isPresent()) {
            usuarioService.delete(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }

    private UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setContrasenaHash(usuario.getContrasenaHash());
        dto.setIdRol(usuario.getIdRol() != null ? usuario.getIdRol().getId() : null);
        return dto;
    }
}

