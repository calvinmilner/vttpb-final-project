import { createReducer, on } from '@ngrx/store';
import { Ingredient } from '../../models';
import { clearIngredients, toggleIngredient } from './ingredients.actions';

export interface IngredientState {
  selectedIngredients: Ingredient[];
}

export const initialIngredientState: IngredientState = {
  selectedIngredients: []
};

export const ingredientReducer = createReducer(
  initialIngredientState,
  on(toggleIngredient, (state, { ingredient }) => {
    const exists = state.selectedIngredients.some(i => i.id === ingredient.id);
    return {
      selectedIngredients: exists
        ? state.selectedIngredients.filter(i => i.id !== ingredient.id)
        : [...state.selectedIngredients, ingredient]
    };
  }),
  on(clearIngredients, state => ({ selectedIngredients: [] }))
);
