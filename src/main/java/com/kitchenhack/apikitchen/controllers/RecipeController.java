package com.kitchenhack.apikitchen.controllers;

import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.servicesimplements.RecipeServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeServiceImplement recipeService;

    // Endpoint: GET /api/recipes/explore
    @GetMapping("/explore")
    public List<Recipe> exploreRecipes() {
        return recipeService.getPublishedRecipes();
    }
}