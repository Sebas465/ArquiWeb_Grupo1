package com.kitchenhack.apikitchen.repositories;

import com.kitchenhack.apikitchen.entities.DiaPlanItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaPlanItemRepository extends JpaRepository<DiaPlanItem, Integer> {
}