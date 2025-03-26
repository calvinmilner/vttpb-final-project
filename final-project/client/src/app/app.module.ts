import { ApplicationConfig, NgModule, isDevMode } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { provideHttpClient } from '@angular/common/http';
import { RecipeService } from './services/recipe.service';
import { RouterModule, Routes } from '@angular/router';
import { SearchComponent } from './components/search.component';
import { RecipeDetailsComponent } from './components/recipe-details.component';
import { PurchaseOrderComponent } from './components/purchase-order.component';
import { ProductService } from './services/product.service';
import { IngredientStore } from './stores/ingredient.store';
import { PriceCheckComponent } from './components/price-check.component';
import { CartStore } from './stores/cart.store';
import { OrderService } from './services/order.service';
import { PaymentService } from './services/payment.service';
import { SuccessComponent } from './components/success.component';
import { StoreModule } from '@ngrx/store';
import { StoreDevtoolsModule } from '@ngrx/store-devtools';
import { EffectsModule } from '@ngrx/effects';
import { ingredientReducer } from './stores/ingredients.reducers';
import { cartReducer } from './stores/cart.reducers';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './material.module';

const appRoutes: Routes = [
  { path: '', component: SearchComponent },
  { path: 'recipes/:id', component: RecipeDetailsComponent },
  { path: 'price', component: PriceCheckComponent },
  { path: 'order', component: PurchaseOrderComponent },
  { path: 'success', component: SuccessComponent }
]

@NgModule({
  declarations: [
    AppComponent,
    SearchComponent,
    RecipeDetailsComponent,
    PurchaseOrderComponent,
    PriceCheckComponent,
    SuccessComponent
  ],
  imports: [
    BrowserModule, 
    ReactiveFormsModule, 
    RouterModule.forRoot(appRoutes, { useHash: true }), 
    StoreModule.forRoot({ ingredients: ingredientReducer, cart: cartReducer }, {}), 
    StoreDevtoolsModule.instrument({ maxAge: 25, logOnly: !isDevMode() }), 
    EffectsModule.forRoot([]),
    BrowserAnimationsModule,
    MaterialModule
  ],
  providers: [provideHttpClient(),
    RecipeService,
    ProductService,
    IngredientStore,
    CartStore,
    OrderService,
    PaymentService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
