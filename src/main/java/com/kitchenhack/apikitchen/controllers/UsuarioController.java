package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.UsuarioDTO;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.servicesinterfaces.IUsuarioService;
import org.modelmapper.ModelMapper;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        ModelMapper m = new ModelMapper();
        // Cada entidad se convierte a DTO para no devolver directamente la entidad JPA.
        List<UsuarioDTO> listaUsuarios = usuarioService.list()
                .stream().map(x -> m.map(x, UsuarioDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaUsuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        ModelMapper m = new ModelMapper();
        java.util.Optional<Usuario> usuario = usuarioService.listId(id);

        if (usuario.isPresent()) {
            UsuarioDTO dto = m.map(usuario.get(), UsuarioDTO.class);
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioDTO dto) {
        ModelMapper m = new ModelMapper();
        // El DTO de entrada se convierte a entidad para que el servicio la persista.
        Usuario u = m.map(dto, Usuario.class);
        Usuario saved = usuarioService.insert(u);
        // La entidad guardada se vuelve a mapear a DTO para la respuesta HTTP.
        UsuarioDTO responseDTO = m.map(saved, UsuarioDTO.class);
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
        u.setContrasenia(dto.getContrasenia());

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
}

