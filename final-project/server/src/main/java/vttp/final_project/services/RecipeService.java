package vttp.final_project.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vttp.final_project.models.*;
import vttp.final_project.utils.Constants;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

@Service
public class RecipeService {

    @Value("${spooncular.api.key}")
    private String spooncularApiKey;

    public List<Recipe> searchRecipes(String query) {
        String url = UriComponentsBuilder.fromUriString(Constants.SPOONCULAR_API_SEARCH)
        .queryParam("apiKey", spooncularApiKey)
        .queryParam("query", query)
        .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);
        
        List<Recipe> recipes = Recipe.toRecipes(resp.getBody());
        
        return recipes;
    }

    public Information getRecipeInformationById(int recipeId) {
        String url = UriComponentsBuilder.fromUriString(Constants.SPOONCULAR_API_RECIPE_INFORMATION)
        .queryParam("apiKey", spooncularApiKey)
        .buildAndExpand(recipeId)
        .toUriString();

        RequestEntity<Void> req = RequestEntity.get(url).accept(MediaType.APPLICATION_JSON).build();

        RestTemplate template = new RestTemplate();
        ResponseEntity<String> resp = template.exchange(req, String.class);

        Information information = Information.toInformation(resp.getBody());
        
        return information;
    }
}
