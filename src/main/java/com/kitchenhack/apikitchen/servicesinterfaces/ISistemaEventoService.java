package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.SistemaEvento;
import java.util.List;
import java.util.Optional;

public interface ISistemaEventoService {
    List<SistemaEvento> list();
    Optional<SistemaEvento> listId(Integer id);
    SistemaEvento insert(SistemaEvento evento);
    void update(SistemaEvento evento);
    void delete(Integer id);
}
