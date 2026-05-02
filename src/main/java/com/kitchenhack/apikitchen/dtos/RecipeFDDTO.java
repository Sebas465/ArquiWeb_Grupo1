package com.kitchenhack.apikitchen.dtos;

import java.util.List;

public class RecipeFDDTO {
    private String title;
    private String difficulty;
    private List<RecipeItemDTO> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public List<RecipeItemDTO> getItems() {
        return items;
    }

    public void setItems(List<RecipeItemDTO> items) {
        this.items = items;
    }
}
