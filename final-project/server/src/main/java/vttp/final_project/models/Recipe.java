package vttp.final_project.models;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Recipe {
    private int recipeId;
    private String title;
    private String imageUrl;

    public int getRecipeId() {
        return recipeId;
    }
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static List<Recipe> toRecipes(String payload) {
        List<Recipe> recipes = new ArrayList<>();
        JsonReader reader = Json.createReader(new StringReader(payload));
        JsonArray recipesArr = reader.readObject().getJsonArray("results");
        for(int i = 0; i < recipesArr.size(); i++) {
            JsonObject recipe = recipesArr.getJsonObject(i);
            Recipe r = new Recipe();
            r.setRecipeId(recipe.getInt("id"));
            r.setTitle(recipe.getString("title"));
            r.setImageUrl(recipe.getString("image"));
            recipes.add(r);
        }
        return recipes;
    }
}
