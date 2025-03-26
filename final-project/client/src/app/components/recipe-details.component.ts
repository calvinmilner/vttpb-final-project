import { Component, Input, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RecipeService } from '../services/recipe.service';
import { Information, Ingredient } from '../../models';
import { Observable } from 'rxjs';
import { first, map } from 'rxjs/operators';
import { Store } from '@ngrx/store';
import { selectSelectedIngredients } from '../stores/ingredients.selectors';
import { clearIngredients, toggleIngredient } from '../stores/ingredients.actions';

@Component({
  selector: 'app-recipe-details',
  standalone: false,
  templateUrl: './recipe-details.component.html',
  styleUrl: './recipe-details.component.css'
})
export class RecipeDetailsComponent implements OnInit {

  @Input()
  ingredients: Ingredient[] = [];

  private store = inject(Store);
  selectedIngredients$ = this.store.select(selectSelectedIngredients);


  private router = inject(Router)
  private recipeService = inject(RecipeService)
  private activatedRoute = inject(ActivatedRoute)
  protected information!: Information
  selectedIngredientsCount$: Observable<number>;

  constructor() {
    this.selectedIngredientsCount$ = this.selectedIngredients$.pipe(
      map(ingredients => ingredients.length)
    );
  }

  ngOnInit(): void {
    this.store.dispatch(clearIngredients());
    this.activatedRoute.params.subscribe(params => {
      const recipeId = params['id']
      console.info('Extracted recipeId: ', recipeId)
      this.recipeService.getRecipeInformationById(recipeId).subscribe(data => {
        console.info('Received recipe information: ', data)
        this.information = data
      })
    })
  }

  toggleSelection(ingredient: Ingredient, checkbox: boolean) {
    if (checkbox) {
      this.store.dispatch(toggleIngredient({ ingredient }));
    }
  }
  proceedToCheckPrice() {

    this.store.select(selectSelectedIngredients).pipe(first()).subscribe(selectedIngredients => {
      if (selectedIngredients.length > 0) {
        this.router.navigate(['/price'], { state: { ingredients: selectedIngredients } });
      } else {
        console.warn('No ingredients selected. Navigation prevented.');
      }
    });
  }
}
