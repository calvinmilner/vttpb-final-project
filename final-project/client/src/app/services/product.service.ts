import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { Observable, tap } from "rxjs";
import { Ingredient, Product } from "../../models";

@Injectable()

export class ProductService {
    
    private http = inject(HttpClient);

    searchProductsForIngredients(ingredients: Ingredient[]) : Observable<Product[]> {
        console.info('Searching for products with ingredients: ', ingredients);
        return this.http.post<Product[]>('/api/products/searchBatch', ingredients).pipe(
            tap(response => console.info('API Response:', response)));
    }
}