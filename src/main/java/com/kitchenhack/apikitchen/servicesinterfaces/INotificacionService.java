package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.dtos.NotificacionDTO;
import java.util.List;

public interface INotificacionService {

    List<NotificacionDTO> listar();
    NotificacionDTO buscarPorId(Integer id);
    NotificacionDTO crear(NotificacionDTO dto);
    NotificacionDTO actualizar(Integer id, NotificacionDTO dto);
    void eliminar(Integer id);
}
