package vttp.final_project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import vttp.final_project.models.*;
import vttp.final_project.services.RecipeService;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeServ;

    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam String query) {
        List<Recipe> recipes = recipeServ.searchRecipes(query);
        return ResponseEntity.ok(recipes);
    }

    @GetMapping("/{id}/information")
    public ResponseEntity<Information> getRecipeInformationById(@PathVariable int id) {
        Information information = recipeServ.getRecipeInformationById(id);
        return ResponseEntity.ok(information);
    }
}
