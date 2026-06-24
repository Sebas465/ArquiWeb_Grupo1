package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Etiqueta;

import java.util.List;
import java.util.Optional;

public interface IEtiquetaService {
    List<Etiqueta> list();
    Etiqueta insert(Etiqueta etiqueta);
    Optional<Etiqueta> listId(Integer id);
    void update(Etiqueta etiqueta);
    void delete(Integer id);
}
