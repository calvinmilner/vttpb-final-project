import { Component, OnInit, inject } from '@angular/core';
import { IngredientStore } from '../stores/ingredient.store';
import { Product } from '../../models';
import { ProductService } from '../services/product.service';
import { Observable, filter, forkJoin, map, switchMap, tap } from 'rxjs';
import { CartStore } from '../stores/cart.store';
import { Router } from '@angular/router';
import { Store } from '@ngrx/store';
import { selectSelectedIngredients } from '../stores/ingredients.selectors';
import { selectCartItems } from '../stores/cart.selectors';
import { addToCart } from '../stores/cart.actions';

@Component({
  selector: 'app-price-check',
  standalone: false,
  templateUrl: './price-check.component.html',
  styleUrl: './price-check.component.css'
})
export class PriceCheckComponent implements OnInit {

  productService = inject(ProductService);

  store = inject(Store);
  selectedIngredients$ = this.store.select(selectSelectedIngredients);

  products$!: Observable<Product[]>;
  cartItems$ = this.store.select(selectCartItems);
  private router = inject(Router);

  ngOnInit(): void {

    this.products$ = this.store.select(selectSelectedIngredients).pipe(
      tap(ingredients => console.info('Selected ingredients:', ingredients)),
      switchMap(ingredients => {
        if (ingredients.length === 0){ return []; }
        return this.productService.searchProductsForIngredients(ingredients);
      }),
      tap(products => console.info('Fetched products:', products))
    );
  }

  addToCart(product: Product, quantity: number) {
    if (!quantity || quantity < 1) {
      alert('Please select a valid quantity');
      return;
    }
    const productWithQuantity = { ...product, quantity };
    this.store.dispatch(addToCart({ product: productWithQuantity }));

  }

  goToCart() {
    this.router.navigate(['/order']);
  }
}
