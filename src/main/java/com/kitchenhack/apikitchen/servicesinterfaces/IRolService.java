package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.Rol;

import java.util.List;
import java.util.Optional;

public interface IRolService {
    public List<Rol> list();
    public Rol insert(Rol rol);
    public void delete(int id);
    public Optional<Rol> listID(int id);
}
