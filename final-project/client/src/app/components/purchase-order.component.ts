import { Component, OnInit, inject } from '@angular/core';
import { ProductService } from '../services/product.service';
import { Order, Product } from '../../models';
import { Observable, first } from 'rxjs';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OrderService } from '../services/order.service';
import { PaymentService } from '../services/payment.service';
import { Store } from '@ngrx/store';
import { selectCartItems, selectTotalPrice } from '../stores/cart.selectors';
import { clearCart } from '../stores/cart.actions';

@Component({
  selector: 'app-purchase-order',
  standalone: false,
  templateUrl: './purchase-order.component.html',
  styleUrl: './purchase-order.component.css'
})
export class PurchaseOrderComponent implements OnInit {

  private store = inject(Store);
cartItems$ = this.store.select(selectCartItems);
totalPrice$ = this.store.select(selectTotalPrice);


  private router = inject(Router);
  private fb = inject(FormBuilder)
  protected form!: FormGroup;
  private order!: Order
  private orderService = inject(OrderService)
  private paymentService = inject(PaymentService)

  ngOnInit(): void {
    this.form = this.createForm();
  }

  createForm() {
    return this.fb.group({
      name: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      email: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      address: this.fb.control<string>('', [Validators.required, Validators.minLength(3)]),
      phone: this.fb.control<string>('', [Validators.required, Validators.pattern('^[0-9]{8,15}$'), Validators.minLength(8)]),
    });
  }

  clearCart() {
    this.store.dispatch(clearCart());

  }

  returnMain() {
    this.store.dispatch(clearCart());
    this.router.navigate(['/']);
  }

  checkout() {
    this.cartItems$.pipe(first()).subscribe(items => {
      this.totalPrice$.pipe(first()).subscribe(total => {
        this.order = this.form.value
        this.order.totalAmount = total
        this.order.cart = { items }

        this.paymentService.createCheckoutSession(this.order).subscribe(response => {
          this.paymentService.redirectToCheckout(response.sessionId);
        });
      });
    });
  }
}
