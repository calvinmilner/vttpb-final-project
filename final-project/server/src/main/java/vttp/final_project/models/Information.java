package vttp.final_project.models;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp.final_project.utils.Constants;

public class Information {
    private int recipeId;
    private String title;
    private String imageUrl;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Instruction> instructions = new ArrayList<>();

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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<Instruction> instructions) {
        this.instructions = instructions;
    }

    public static Information toInformation(String json) {
        JsonReader reader = Json.createReader(new StringReader(json));
        JsonObject obj = reader.readObject();
        Information info = new Information();

        info.setRecipeId(obj.getInt("id"));
        info.setTitle(obj.getString("title", ""));
        info.setImageUrl(obj.getString("image", ""));

        JsonArray ingredientsArr = obj.getJsonArray("extendedIngredients");
        if (ingredientsArr != null) {
            for (int i = 0; i < ingredientsArr.size(); i++) {
                JsonObject ingredient = ingredientsArr.getJsonObject(i);
                Ingredient ing = new Ingredient();
                ing.setId(ingredient.getInt("id", 0));
                ing.setCategory(ingredient.getString("aisle", "unknown"));
                ing.setName(ingredient.getString("name", "unknown"));
                ing.setImageUrl(Constants.SPOONCULAR_IMG_PREFIX + ingredient.getString("image", ""));
                JsonObject measures = ingredient.getJsonObject("measures");
                if (measures != null) {
                    JsonObject metric = measures.getJsonObject("metric");
                    if (metric != null) {
                        ing.setAmount(metric.getJsonNumber("amount").doubleValue());
                        ing.setUnit(metric.getString("unitShort", ""));
                    }
                }
                info.getIngredients().add(ing);
            }
        }

        JsonArray instructionsArr = obj.getJsonArray("analyzedInstructions");
        if (instructionsArr != null) {
            JsonObject first = instructionsArr.getJsonObject(0);
            JsonArray stepsArr = first.getJsonArray("steps");
            if (stepsArr != null && !stepsArr.isEmpty()) {
                for (int i = 0; i < stepsArr.size(); i++) {
                    JsonObject instruction = stepsArr.getJsonObject(i);
                    Instruction ins = new Instruction();
                    ins.setStep(instruction.getInt("number", 0));
                    ins.setInstruction(instruction.getString("step", "No instruction available"));
                    JsonArray equipmentArr = instruction.getJsonArray("equipment");
                    if (equipmentArr != null && !equipmentArr.isEmpty()) {
                        ins.setEquipmentName(equipmentArr.getJsonObject(0).getString("name", ""));
                    } else {
                        ins.setEquipmentName("No equipment");
                    }
                    info.getInstructions().add(ins);
                }
            }
        }

        return info;
    }

}
