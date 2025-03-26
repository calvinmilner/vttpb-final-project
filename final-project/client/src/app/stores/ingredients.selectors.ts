import { createSelector, createFeatureSelector } from '@ngrx/store';
import { IngredientState } from './ingredients.reducers';

export const selectIngredientState = createFeatureSelector<IngredientState>('ingredients');

export const selectSelectedIngredients = createSelector(
    selectIngredientState,
    state => state.selectedIngredients
);
