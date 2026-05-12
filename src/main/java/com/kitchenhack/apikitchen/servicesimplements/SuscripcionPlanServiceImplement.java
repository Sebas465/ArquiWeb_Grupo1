package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.SuscripcionPlan;
import com.kitchenhack.apikitchen.repositories.SuscripcionPlanRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.ISuscripcionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Implementación del servicio de suscripciones a planes
@Service
public class SuscripcionPlanServiceImplement implements ISuscripcionPlanService {

    @Autowired
    private SuscripcionPlanRepository suscripcionPlanRepository;

    @Override
    public SuscripcionPlan insert(SuscripcionPlan suscripcion) {
        return suscripcionPlanRepository.save(suscripcion);
    }

    @Override
    public Optional<SuscripcionPlan> listId(Integer id) {
        return suscripcionPlanRepository.findById(id);
    }

    @Override
    public boolean existeSubscripcionActiva(Long usuarioId, Integer idPlan) {
        return suscripcionPlanRepository.existsByIdUsuario_IdAndIdPlan_IdAndActivoTrue(usuarioId, idPlan);
    }

    @Override
    public List<SuscripcionPlan> listActivasByUsuario(Long usuarioId) {
        return suscripcionPlanRepository.findByIdUsuario_IdAndActivoTrue(usuarioId);
    }
}
