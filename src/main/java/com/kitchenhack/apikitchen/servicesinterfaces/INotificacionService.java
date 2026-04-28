package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Notificacion;

import java.util.List;
import java.util.Optional;

public interface INotificacionService {

    // Lista todas las notificaciones.
    List<Notificacion> list();

    // Inserta una nueva notificacion.
    Notificacion insert(Notificacion notificacion);

    // Actualiza una notificacion existente.
    void update(Notificacion notificacion);

    // Busca por id.
    Optional<Notificacion> listId(Integer id);

    // Elimina por id.
    void delete(Integer id);
}
