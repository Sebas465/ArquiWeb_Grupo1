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
            return dto;
        }).collect(java.util.stream.Collectors.toList());

        return ResponseEntity.ok(listaUsuarios);
    }

}
