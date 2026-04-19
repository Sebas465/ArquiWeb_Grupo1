package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.dtos.NotificacionDTO;
import com.kitchenhack.apikitchen.entities.Notificacion;
import com.kitchenhack.apikitchen.entities.Usuario;
import com.kitchenhack.apikitchen.repositories.NotificacionRepository;
import com.kitchenhack.apikitchen.repositories.UsuarioRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.INotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImplement implements INotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Convertir Entity → DTO
    private NotificacionDTO toDTO(Notificacion n) {
        NotificacionDTO dto = new NotificacionDTO();
        dto.setId(n.getId());
        dto.setIdUsuario(n.getUsuario().getId());
        dto.setTipo(n.getTipo());
        dto.setTitulo(n.getTitulo());
        dto.setCuerpo(n.getCuerpo());
        dto.setLeida(n.getLeida());
        dto.setFechaEnvio(n.getFechaEnvio());
        return dto;
    }

    // Convertir DTO → Entity
    private Notificacion toEntity(NotificacionDTO dto) {
        Notificacion n = new Notificacion();
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getIdUsuario()));
        n.setUsuario(usuario);
        n.setTipo(dto.getTipo());
        n.setTitulo(dto.getTitulo());
        n.setCuerpo(dto.getCuerpo());
        n.setLeida(dto.getLeida() != null ? dto.getLeida() : false);
        n.setFechaEnvio(dto.getFechaEnvio());
        return n;
    }

    @Override
    public List<NotificacionDTO> listar() {
        return notificacionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificacionDTO buscarPorId(Integer id) {
        Notificacion n = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificacion no encontrada con id: " + id));
        return toDTO(n);
    }

    @Override
    public NotificacionDTO crear(NotificacionDTO dto) {
        Notificacion n = toEntity(dto);
        return toDTO(notificacionRepository.save(n));
    }

    @Override
    public NotificacionDTO actualizar(Integer id, NotificacionDTO dto) {
        Notificacion n = notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificacion no encontrada con id: " + id));
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + dto.getIdUsuario()));
        n.setUsuario(usuario);
        n.setTipo(dto.getTipo());
        n.setTitulo(dto.getTitulo());
        n.setCuerpo(dto.getCuerpo());
        n.setLeida(dto.getLeida());
        n.setFechaEnvio(dto.getFechaEnvio());
        return toDTO(notificacionRepository.save(n));
    }

    @Override
    public void eliminar(Integer id) {
        notificacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notificacion no encontrada con id: " + id));
        notificacionRepository.deleteById(id);
    }
}
