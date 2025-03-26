package vttp.final_project.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

import vttp.final_project.models.Order;
import vttp.final_project.services.EmailService;
import vttp.final_project.services.OrderService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private EmailService emailService;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${frontend.base.url}")
    private String frontendBaseUrl;

    private final Map<String, Order> pendingOrders = new ConcurrentHashMap<>();

    @PostMapping("/create-checkout-session")
    public Map<String, String> createCheckoutSession(@RequestBody Order order) throws StripeException {
        
        Stripe.apiKey = stripeSecretKey;

        long amount = (long) (order.getTotalAmount() * 100);

        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(frontendBaseUrl + "/#/success?sessionId={CHECKOUT_SESSION_ID}")
                .setCancelUrl(frontendBaseUrl + "/#/order")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("sgd")
                                .setUnitAmount(amount)
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName("Recipe Finder App Order Payment")
                                        .build())
                                .build())
                        .build())
                .build();

        Session session = Session.create(params);

        pendingOrders.put(session.getId(), order);

        Map<String, String> response = new HashMap<>();
        response.put("sessionId", session.getId());
        return response;
    }

    @GetMapping("/confirm-payment")
    public ResponseEntity<Map<String, String>> confirmPayment(@RequestParam String sessionId) throws StripeException {
        Stripe.apiKey = stripeSecretKey;
        Session session = Session.retrieve(sessionId);

        if ("paid".equals(session.getPaymentStatus())) {
            Order order = pendingOrders.remove(sessionId);
            orderService.createNewPurchaseOrder(order);
            emailService.sendOrderConfirmation(order);

            Map<String, String> response = new HashMap<>();
            response.put("orderId", String.valueOf(order.getOrderId()));
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Payment not completed"));
    }
}
