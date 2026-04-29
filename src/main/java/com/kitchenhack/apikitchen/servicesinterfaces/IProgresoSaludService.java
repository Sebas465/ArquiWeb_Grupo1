package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.ProgresoSalud;
import com.kitchenhack.apikitchen.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface IProgresoSaludService {
    List<ProgresoSalud> list();
    Optional<ProgresoSalud> listId(Integer id);
    ProgresoSalud insert(ProgresoSalud progresoSalud);
    ProgresoSalud update(ProgresoSalud progresoSalud);
    void delete(Integer id);

    // US-P3-02: historial por usuario ordenado por fecha DESC
    List<ProgresoSalud> listByUsuario(Usuario usuario);
}
