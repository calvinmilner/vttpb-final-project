import { createAction, props } from '@ngrx/store';
import { Product } from '../../models';


export const addToCart = createAction('[Cart] Add Product', props<{ product: Product }>());
export const removeFromCart = createAction('[Cart] Remove Product', props<{ productName: string }>());
export const clearCart = createAction('[Cart] Clear Cart');
