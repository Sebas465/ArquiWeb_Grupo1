package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.DiaPlanItem;
import com.kitchenhack.apikitchen.repositories.DiaPlanItemRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IDiaPlanItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaPlanItemServiceImplement implements IDiaPlanItemService {

    @Autowired
    private DiaPlanItemRepository dR;

    @Override
    public DiaPlanItem insert(DiaPlanItem diaPlanItem) {
        return dR.save(diaPlanItem);
    }

    @Override
    public List<DiaPlanItem> list() {
        return dR.findAll();
    }

    @Override
    public Optional<DiaPlanItem> listId(Integer id) {
        return dR.findById(id);
    }

    @Override
    public void delete(Integer id) {
        dR.deleteById(id);
    }
}