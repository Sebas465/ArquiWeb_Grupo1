package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.dtos.NotificacionDTO;
import com.kitchenhack.apikitchen.dtos.RolDTO;
import com.kitchenhack.apikitchen.entities.Rol;
import com.kitchenhack.apikitchen.servicesinterfaces.IRolService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Rol")
public class RolController {
    @Autowired
    private IRolService iRolService;

    @GetMapping("/Listar")
    public ResponseEntity<List<RolDTO>> listar() {
        List<Rol> listaEntidades = iRolService.list();

        ModelMapper m = new ModelMapper();
        List<RolDTO> listaDTO = listaEntidades.stream()
                .map(rol -> m.map(rol, RolDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTO);
    }

    @PostMapping("/Registrar")
    public ResponseEntity<?> registrar(@RequestBody RolDTO dto){
        if (dto.getId() <= 0 ||
                dto.getNombre().isEmpty() || dto.getDescripcion().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Todos los campos son obligatorios");
        }

        ModelMapper m=new ModelMapper();
        Rol l=m.map(dto, Rol.class);
        Rol lv= iRolService.insert(l);
        RolDTO responseDTO=m.map(lv,RolDTO.class);
        return  ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @DeleteMapping("/{id}/Eliminar")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        Optional<Rol> rol = iRolService.listID(id);

        if (rol.isPresent()) {
            iRolService.delete(id);
            return ResponseEntity.ok("Rol eliminado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Rol no existe");
        }
    }
}
