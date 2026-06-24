package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Rol;

import java.util.List;
import java.util.Optional;

public interface IRolService {
    List<Rol> list();
    Rol insert(Rol rol);
    Optional<Rol> listId(Integer id);
    void update(Rol rol);
    void delete(Integer id);
}
