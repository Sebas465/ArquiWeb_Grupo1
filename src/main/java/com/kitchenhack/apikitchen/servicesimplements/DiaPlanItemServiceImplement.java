package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.DiaPlanItem;
import com.kitchenhack.apikitchen.repositories.DiaPlanItemRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IDiaPlanItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// Implementación del servicio de ítems de días del plan
@Service
public class DiaPlanItemServiceImplement implements IDiaPlanItemService {

    @Autowired
    private DiaPlanItemRepository diaPlanItemRepository;

    @Override
    public DiaPlanItem insert(DiaPlanItem item) {
        return diaPlanItemRepository.save(item);
    }

    @Override
    public Optional<DiaPlanItem> listId(Integer id) {
        return diaPlanItemRepository.findById(id);
    }

    @Override
    public void delete(Integer id) {
        diaPlanItemRepository.deleteById(id);
    }
}
