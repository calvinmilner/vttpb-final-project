import { createAction, props } from '@ngrx/store';
import { Ingredient } from '../../models';


export const toggleIngredient = createAction('[Ingredient] Toggle Ingredient',props<{ ingredient: Ingredient }>());

export const clearIngredients = createAction('[Ingredient] Clear All');
