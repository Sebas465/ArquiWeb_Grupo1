package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.PlanMaestro;
import com.kitchenhack.apikitchen.repositories.PlanMaestroRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IPlanMaestroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Implementación del servicio de planes maestros — delega en el repositorio JPA
@Service
public class PlanMaestroServiceImplement implements IPlanMaestroService {

    @Autowired
    private PlanMaestroRepository planMaestroRepository;

    @Override
    public List<PlanMaestro> list() {
        return planMaestroRepository.findAll();
    }

    @Override
    public PlanMaestro insert(PlanMaestro plan) {
        return planMaestroRepository.save(plan);
    }

    @Override
    public Optional<PlanMaestro> listId(Integer id) {
        return planMaestroRepository.findById(id);
    }

    @Override
    public void update(PlanMaestro plan) {
        planMaestroRepository.save(plan);
    }

    @Override
    public void delete(Integer id) {
        planMaestroRepository.deleteById(id);
    }

    @Override
    public List<PlanMaestro> listByTipo(String tipoPlan) {
        return planMaestroRepository.findByTipoPlan(tipoPlan);
    }
}
