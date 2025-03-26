import { createReducer, on } from '@ngrx/store';
import { Product } from '../../models';
import { addToCart, clearCart, removeFromCart } from './cart.actions';

export interface CartState {
    items: Product[];
}

export const initialCartState: CartState = {
    items: []
};

export const cartReducer = createReducer(
    initialCartState,
    on(addToCart, (state, { product }) => {
        const exists = state.items.find(p => p.productName === product.productName);
        return {
            items: exists
                ? state.items.map(p =>
                    p.productName === product.productName
                        ? { ...p, quantity: (p.quantity || 0) + (product.quantity || 1) }
                        : p
                )
                : [...state.items, product]
        };
    }),
    on(removeFromCart, (state, { productName }) => ({
        items: state.items.filter(p => p.productName !== productName)
    })),
    on(clearCart, () => ({ items: [] }))
);
