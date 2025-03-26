import { Component, OnInit, inject } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CartStore } from '../stores/cart.store';
import { HttpClient } from '@angular/common/http';

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
  private cartStore = inject(CartStore);
  protected orderId!: string

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
          alert(`Order placed successfully!! An email confirmation has been sent to you.\n\nYou will be redirected in 30s.\n\n Order ID: ${res.orderId}`);
          this.cartStore.clearCart();
          setTimeout(() => { this.router.navigate(['/']); }, 30000);
        },
        error: () => {
          alert('Payment verification failed!! Please try again.');
          this.router.navigate(['/order']);
        }
      });
  }
}
