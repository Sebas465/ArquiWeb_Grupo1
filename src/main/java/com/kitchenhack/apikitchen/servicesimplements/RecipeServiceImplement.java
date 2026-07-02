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
    public Optional<Recipe> listId(Integer id) {
        return id == null ? Optional.empty() : recipeRepository.findById(id);
    }


    @Override
    public void delete(Integer id) {
        if (id != null) {
            recipeRepository.deleteById(id);
        }
    }

    @Override
    public RecetaDetalle registrarDetalle(RecetaDetalle detalle) {
        return recetaDetalleRepository.save(detalle);
    }

    @Override
    public void deleteDetalle(Integer detalleId) {
        if (detalleId != null) {
            recetaDetalleRepository.deleteById(detalleId);
        }
    }

    @Override
    public void actualizarOrdenDetalle(Integer id, Integer nuevoOrden) {
        recetaDetalleRepository.findById(id).ifPresent(det -> {
            det.setOrden(nuevoOrden);
            recetaDetalleRepository.save(det);
        });
    }

    @Override
    public List<Object[]> getRecipeStatsByDifficulty() {
        // Llama a la query que pusiste en IRecipeRepository
        return recipeRepository.getRecipeStatsByDifficulty();
    }

    // En la implementación:
    @Override
    public List<Object[]> reportTopIngredients() {
        return recetaDetalleRepository.topMostUsedIngredients();
    }

}