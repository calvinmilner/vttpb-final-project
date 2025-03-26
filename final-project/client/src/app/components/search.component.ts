import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { RecipeService } from '../services/recipe.service';
import { Recipe } from '../../models';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-search',
  standalone: false,
  templateUrl: './search.component.html',
  styleUrl: './search.component.css',
})
export class SearchComponent implements OnInit {
  
  private recipeService = inject(RecipeService)

  private fb = inject(FormBuilder)
  protected form!: FormGroup
  protected recipes: Recipe[] = []

  ngOnInit(): void {
    this.form = this.createForm()
  }

  createForm() : FormGroup {
    return this.fb.group({
      query: this.fb.control<string>('', [Validators.required, Validators.minLength(1)])
    })
  }

  search() {
    console.info('Querying for: ', this.form.value.query)
    this.recipeService.searchRecipes(this.form.value.query).subscribe(data => (this.recipes = data))
  }
}
