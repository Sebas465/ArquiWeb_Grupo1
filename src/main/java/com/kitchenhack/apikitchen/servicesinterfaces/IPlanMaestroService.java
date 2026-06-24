package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.PlanMaestro;
import java.util.List;
import java.util.Optional;

public interface IPlanMaestroService {
    PlanMaestro insert(PlanMaestro planMaestro);
    List<PlanMaestro> list();
    Optional<PlanMaestro> listId(Integer id);
    void delete(Integer id);
    List<PlanMaestro> findByTipoPlan(String tipoPlan);
    void update(PlanMaestro planMaestro);
}