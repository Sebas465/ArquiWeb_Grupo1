package com.kitchenhack.apikitchen.servicesimplements;

import com.kitchenhack.apikitchen.entities.RecetaDetalle;
import com.kitchenhack.apikitchen.entities.Recipe;
import com.kitchenhack.apikitchen.repositories.RecetaDetalleRepository;
import com.kitchenhack.apikitchen.repositories.RecipeRepository;
import com.kitchenhack.apikitchen.servicesinterfaces.IRecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImplement implements IRecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecetaDetalleRepository recetaDetalleRepository;

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
        return id == null ? Optional.empty() : recipeRepository.findById(id);
    }


    @Override
    public void delete(Long id) {
        if (id != null) {
            recipeRepository.deleteById(id);
        }
    }

    @Override
    public List<Recipe> findByDifficulty(String difficulty) {
        return recipeRepository.findByDifficulty(difficulty);
    }

    @Override
    public List<Recipe> findByPublishedTrue() {
        return recipeRepository.findByPublishedTrue();
    }

    @Override
    public RecetaDetalle registrarDetalle(RecetaDetalle detalle) {
        return recetaDetalleRepository.save(detalle);
    }

}