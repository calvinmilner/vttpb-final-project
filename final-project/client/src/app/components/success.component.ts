import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Store } from '@ngrx/store';
import { clearCart } from '../stores/cart.actions';
import { NgZone } from '@angular/core';

@Component({
  selector: 'app-success',
  standalone: false,
  templateUrl: './success.component.html',
  styleUrl: './success.component.css'
})
export class SuccessComponent implements OnInit {
  private http = inject(HttpClient);
  private activatedRoute = inject(ActivatedRoute);
  private router = inject(Router);
  protected orderId!: string
  private store = inject(Store);
  private zone = inject(NgZone)

  ngOnInit(): void {
    const sessionId = this.activatedRoute.snapshot.queryParamMap.get('sessionId');
    if (!sessionId) {
      alert('Invalid payment session');
      this.router.navigate(['/']);
      return;
    }

    this.http.get<{ orderId: string }>(`/api/payment/confirm-payment?sessionId=${sessionId}`)
      .subscribe({
        next: (res) => {
          this.orderId = res.orderId;
          alert(`Order placed successfully!! An email confirmation has been sent to you.\n\nYou will be redirected in 15s after clicking OK.\n\n Order ID: ${res.orderId}`);
          this.store.dispatch(clearCart());
          this.zone.run(() => {
            setTimeout(() => {
              this.router.navigate(['/']);
            }, 15000);
          });
        },
        error: () => {
          alert('Payment verification failed!! Please try again.');
          this.router.navigate(['/order']);
        }
      });
  }
}
