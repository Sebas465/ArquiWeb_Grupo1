package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.UsuarioDTO;
import com.kitchenhack.apikitchen.entities.Rol;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/nuevo")
    public ResponseEntity<?> registrar(@RequestBody UsuarioDTO dto) {
        if (dto.getContrasenaHash() == null || dto.getContrasenaHash().isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("contraseña es requerido");
        }
        // Validar unicidad de email y username
        if ((dto.getEmail() != null && usuarioService.existsByEmail(dto.getEmail())) ||
                (dto.getUsername() != null && usuarioService.existsByUsername(dto.getUsername()))) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email o username ya registrado");
        }

        ModelMapper m = new ModelMapper();
        Usuario u = m.map(dto, Usuario.class);
        u.setContrasenaHash(passwordEncoder.encode(dto.getContrasenaHash()));
        Rol rol = new Rol();
        rol.setId(dto.getIdRol() != null ? dto.getIdRol() : 1);
        u.setIdRol(rol);
        LocalDateTime now = LocalDateTime.now();
        u.setFechaRegistro(now);
        u.setUltimaActividad(now);

        Usuario saved = usuarioService.insert(u);
        UsuarioDTO responseDTO = m.map(saved, UsuarioDTO.class);
        // No exponer contrasenaHash en la respuesta
        responseDTO.setContrasenaHash(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        List<Usuario> usuarios = usuarioService.list();

        if (usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay usuarios registrados");
        }

        ModelMapper m = new ModelMapper();
        List<UsuarioDTO> listaUsuarios = usuarios.stream().map(u -> {
            UsuarioDTO dto = m.map(u, UsuarioDTO.class);
            dto.setContrasenaHash(null); // Seguridad: Limpiamos la contraseña antes de enviarla
            dto.setNombreRol(u.getIdRol() != null ? u.getIdRol().getNombre() : null);
            return dto;
        }).collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(listaUsuarios);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        java.util.Optional<Usuario> usuario = usuarioService.listId(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        ModelMapper m = new ModelMapper();
        UsuarioDTO dto = m.map(usuario.get(), UsuarioDTO.class);
        dto.setContrasenaHash(null);
        dto.setNombreRol(usuario.get().getIdRol() != null ? usuario.get().getIdRol().getNombre() : null);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody UsuarioDTO dto) {
        java.util.Optional<Usuario> existente = usuarioService.listId(id);
        if (existente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        Usuario u = existente.get();
        u.setUsername(dto.getUsername());
        u.setNombre(dto.getNombre());
        u.setApellido(dto.getApellido());
        u.setEmail(dto.getEmail());
        if (dto.getIdRol() != null) {
            Rol rol = new Rol();
            rol.setId(dto.getIdRol());
            u.setIdRol(rol);
        }
        if (dto.getContrasenaHash() != null && !dto.getContrasenaHash().isBlank()) {
            u.setContrasenaHash(passwordEncoder.encode(dto.getContrasenaHash()));
        }
        u.setUltimaActividad(LocalDateTime.now());

        usuarioService.update(u);
        return ResponseEntity.ok("Usuario actualizado correctamente");
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        java.util.Optional<Usuario> usuario = usuarioService.listId(id);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        usuarioService.delete(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }

}
