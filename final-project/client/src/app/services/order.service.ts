import { Injectable, inject } from "@angular/core";
import { Order } from "../../models";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";

@Injectable()
export class OrderService {

    private http = inject(HttpClient);

    submitOrder(order: Order) : Observable<string> {
        return this.http.post<string>('/api/orders', order);
    }
}