import { HttpClient } from "@angular/common/http";
import { inject, Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { Information, Recipe } from "../../models";

@Injectable()
export class RecipeService {

    private http = inject(HttpClient)

    searchRecipes(query:string) : Observable<Recipe[]> {
        return this.http.get<Recipe[]>(`/api/recipes/search?query=${query}`).pipe(tap(response => console.info('Received response: ', response)))
    }

    getRecipeInformationById(id: number) : Observable<Information> {
        return this.http.get<Information>(`/api/recipes/${id}/information`).pipe(tap(response => console.info('Received response: ', response)))
    }
}