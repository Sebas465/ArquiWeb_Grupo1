package com.kitchenhack.apikitchen.service;

import com.kitchenhack.apikitchen.model.Recipe;
import com.kitchenhack.apikitchen.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    public List<Recipe> getPublishedRecipes() {
        return recipeRepository.findByPublishedTrue();
    }
}