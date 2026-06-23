package com.kitchenhack.apikitchen.servicesinterfaces;

import com.kitchenhack.apikitchen.entities.DiaPlanItem;
import java.util.List;
import java.util.Optional;

public interface IDiaPlanItemService {
    DiaPlanItem insert(DiaPlanItem diaPlanItem);
    List<DiaPlanItem> list();
    Optional<DiaPlanItem> listId(Integer id);
    void delete(Integer id);
}