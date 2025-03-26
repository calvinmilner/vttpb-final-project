import { Injectable } from "@angular/core";
import { ComponentStore } from "@ngrx/component-store";
import { Ingredient, IngredientSlice } from "../../models";
const INIT: IngredientSlice = {
    selectedIngredients: []
}

@Injectable()
export class IngredientStore extends ComponentStore<IngredientSlice> {
    constructor() {
        super(INIT);
    }

readonly selectedIngredients$ = this.select(state => state.selectedIngredients);

  readonly toggleIngredient = this.updater((state, ingredient: Ingredient) => {
    const isSelected = state.selectedIngredients.some(i => i.id === ingredient.id);
    return {
      selectedIngredients: isSelected
        ? state.selectedIngredients.filter(i => i.id !== ingredient.id)
        : [...state.selectedIngredients, ingredient]
    };
  });

  readonly clearSelection = this.updater((state) => ({
    selectedIngredients: []
  }));
}