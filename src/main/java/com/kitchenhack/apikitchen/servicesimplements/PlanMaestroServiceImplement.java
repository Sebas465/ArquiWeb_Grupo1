package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.PlanMaestro;
import com.kitchenhack.apikitchen.repositories.PlanMaestroRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IPlanMaestroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlanMaestroServiceImplement implements IPlanMaestroService {

    @Autowired
    private PlanMaestroRepository pR;

    @Override
    public PlanMaestro insert(PlanMaestro planMaestro) {
        return pR.save(planMaestro);
    }

    @Override
    public List<PlanMaestro> list() {
        return pR.findAll();
    }

    @Override
    public Optional<PlanMaestro> listId(Integer id) {
        return pR.findById(id);
    }

    @Override
    public void delete(Integer id) {
        pR.deleteById(id);
    }

    @Override
    public List<PlanMaestro> findByTipoPlan(String tipoPlan) {
        return pR.findByTipoPlan(tipoPlan);
    }

    @Override
    public void update(PlanMaestro planMaestro) {
        pR.save(planMaestro);
    }
}