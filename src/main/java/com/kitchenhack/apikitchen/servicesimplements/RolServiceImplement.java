package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.Rol;
import com.kitchenhack.apikitchen.repositories.RolRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IRolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RolServiceImplement implements IRolService {
    @Autowired
    private RolRepository rolRepository;

    @Override
    public List<Rol> list() {
        return rolRepository.findAll();
    }

    @Override
    public Rol insert(Rol rol) {
        return rolRepository.save(rol);
    }

    @Override
    public void delete(int id) {
        rolRepository.deleteById(id);
    }

    @Override
    public Optional<Rol> listID(int id) {
        return rolRepository.findById(id);
    }
}
