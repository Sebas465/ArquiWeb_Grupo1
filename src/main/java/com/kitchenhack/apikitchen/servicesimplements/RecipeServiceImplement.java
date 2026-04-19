package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImplement {

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> getPublishedRecipes() {
        return recipeRepository.findByPublishedTrue();
    }
}