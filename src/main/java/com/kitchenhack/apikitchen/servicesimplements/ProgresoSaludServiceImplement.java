package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.dtos.ProgresoSaludDTO;
import com.kitchenhack.apikitchen.entities.ProgresoSalud;
import com.kitchenhack.apikitchen.repositories.ProgresoSaludRepository;
import com.kitchenhack.apikitchen.repositories.UsuarioRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IProgresoSaludService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgresoSaludServiceImplement implements IProgresoSaludService {

    @Autowired
    private ProgresoSaludRepository progresoSaludRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private ProgresoSaludDTO toDTO(ProgresoSalud progreso) {
        ProgresoSaludDTO dto = new ProgresoSaludDTO();
        dto.setId(progreso.getId());
        dto.setUsuarioId(progreso.getUsuarioId());
        dto.setAlergias(progreso.getAlergias());
        return dto;
    }

    private ProgresoSalud toEntity(ProgresoSaludDTO dto) {
        if (dto.getUsuarioId() != null) {
            usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUsuarioId()));
        }
        ProgresoSalud progreso = new ProgresoSalud();
        progreso.setUsuarioId(dto.getUsuarioId());
        progreso.setAlergias(dto.getAlergias());
        return progreso;
    }

    @Override
    public List<ProgresoSaludDTO> listarTodos() {
        return progresoSaludRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ProgresoSaludDTO buscarPorId(Integer id) {
        ProgresoSalud progreso = progresoSaludRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Progreso de salud no encontrado con id: " + id));
        return toDTO(progreso);
    }

    @Override
    public ProgresoSaludDTO crear(ProgresoSaludDTO dto) {
        return toDTO(progresoSaludRepository.save(toEntity(dto)));
    }

    @Override
    public ProgresoSaludDTO actualizar(Integer id, ProgresoSaludDTO dto) {
        ProgresoSalud progreso = progresoSaludRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Progreso de salud no encontrado con id: " + id));
        if (dto.getUsuarioId() != null) {
            usuarioRepository.findById(dto.getUsuarioId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getUsuarioId()));
        }
        progreso.setUsuarioId(dto.getUsuarioId());
        progreso.setAlergias(dto.getAlergias());
        return toDTO(progresoSaludRepository.save(progreso));
    }

    @Override
    public void eliminar(Integer id) {
        progresoSaludRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Progreso de salud no encontrado con id: " + id));
        progresoSaludRepository.deleteById(id);
    }
}

