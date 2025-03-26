import { createSelector, createFeatureSelector } from '@ngrx/store';
import { CartState } from './cart.reducers';


export const selectCartState = createFeatureSelector<CartState>('cart');

export const selectCartItems = createSelector(
    selectCartState,
    state => state.items
);

export const selectTotalPrice = createSelector(
    selectCartItems,
    items => items.reduce((total, p) => total + p.price * (p.quantity || 1), 0)
);
