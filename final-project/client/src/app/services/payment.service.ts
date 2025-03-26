import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Stripe, loadStripe } from '@stripe/stripe-js';
import { Order } from '../../models';

@Injectable()
export class PaymentService {

    private http = inject(HttpClient);
    private stripePromise = loadStripe('pk_test_51R5jCVImLpr46uwfbU89Hkh9Le1WxYLM7O1h37m2tlWu2tguRxhNH94Asyw3B2tDviwe44hjkQPZ0I7J93B67FAe00qd8qdNAz');
    createCheckoutSession(order: Order): Observable<{ sessionId: string }> {
        return this.http.post<{ sessionId: string }>('/api/payment/create-checkout-session', order);
    }

    async redirectToCheckout(sessionId: string) {
        const stripe = await this.stripePromise;
        await stripe?.redirectToCheckout({ sessionId });
    }
}