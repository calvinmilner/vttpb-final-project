import { Injectable } from "@angular/core";
import { CartSlice, Product } from "../../models";
import { ComponentStore } from "@ngrx/component-store";
import { Observable, map } from "rxjs";


const INIT: CartSlice = {
    items: []
}

@Injectable()
export class CartStore extends ComponentStore<CartSlice> {
    constructor() {
        super(INIT);
    }

    readonly cartItems$ = this.select(state => state.items);

    readonly totalPrice$: Observable<number> = this.cartItems$.pipe(
        map(items => items.reduce((total, product) => total + (product.price * (product.quantity || 1)), 0))
    );

    readonly addToCart = this.updater((state, product: Product) => {
        const existingItem = state.items.find(p => p.productName === product.productName);
        return {
            items: existingItem
                ? state.items.map(p => p.productName === product.productName ? { ...p, quantity: (p.quantity || 0) + product.quantity! } : p)
                : [...state.items, product]
        };
    });

    readonly removeFromCart = this.updater((state, productName: string) => ({
        items: state.items.filter(p => p.productName !== productName)
    }));

    readonly clearCart = this.updater(() => ({ items: [] }));

    readonly getCart = this.select<CartSlice>((slice: CartSlice) => {
        return slice
    })
}