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
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public ResponseEntity<?> listar() {
        ModelMapper m = new ModelMapper();
        // Cada entidad se convierte a DTO para no devolver directamente la entidad JPA.
        List<UsuarioDTO> listaUsuarios = usuarioService.list()
                .stream().map(x -> m.map(x, UsuarioDTO.class))
                .collect(Collectors.toList());
        if (listaUsuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay usuarios registrados");
        }
        return ResponseEntity.ok(listaUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        ModelMapper m = new ModelMapper();
        java.util.Optional<Usuario> usuario = usuarioService.listId(id);

        if (usuario.isPresent()) {
            // Optional presente: convertir la entidad a DTO con ModelMapper
            UsuarioDTO dto = m.map(usuario.get(), UsuarioDTO.class);
            // Asegurar que el campo contrasenaHash no se exponga
            dto.setContrasenaHash(null);
            return ResponseEntity.ok(dto);
        } else {
            // Si el Optional viene vacío, informar que no existe el recurso
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }

    @GetMapping("/buscar-email")
    public ResponseEntity<?> buscarPorEmail(@RequestParam String email) {
        ModelMapper m = new ModelMapper();
        java.util.Optional<Usuario> usuario = usuarioService.findByEmail(email);

        if (usuario.isPresent()) {
            UsuarioDTO dto = m.map(usuario.get(), UsuarioDTO.class);
            dto.setContrasenaHash(null);
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
    }


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

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
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
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        java.util.Optional<Usuario> usuario = usuarioService.listId(id);

        if (usuario.isPresent()) {
            usuarioService.delete(id);
            return ResponseEntity.ok("Usuario eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }

}

