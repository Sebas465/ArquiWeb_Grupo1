package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.repositories.RecipeRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImplement implements IRecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Override
    public List<Recipe> list() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe insert(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public void update(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    @Override
    public Optional<Recipe> listId(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public List<Recipe> explorePublished(Integer categoriaId, BigDecimal maxCal) {
        return recipeRepository.findPublishedRecipes(categoriaId, maxCal);
    }
}