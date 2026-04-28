package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.Notificacion;
import com.kitchenhack.apikitchen.repositories.NotificacionRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.INotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImplement implements INotificacionService {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Override
    public List<Notificacion> list() {
        return notificacionRepository.findAll();
    }

    @Override
    public Notificacion insert(Notificacion notificacion) {
        // Dejar la validación de existencia de Usuario al controlador (consistencia con UsuarioController).
        return notificacionRepository.save(notificacion);
    }

    @Override
    public void update(Notificacion notificacion) {
        // Repositorio guarda la entidad actualizada. La verificación de existencia y el reuso de la entidad
        // se realizan en el controlador (seguir patrón UsuarioController).
        notificacionRepository.save(notificacion);
    }

    @Override
    public Optional<Notificacion> listId(Integer id) {
        return notificacionRepository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        notificacionRepository.deleteById(id);
    }
}
