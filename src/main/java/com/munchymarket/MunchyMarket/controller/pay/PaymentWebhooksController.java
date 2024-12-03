package com.munchymarket.MunchyMarket.controller.pay;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Charge;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webhooks")
public class PaymentWebhooksController {


    @Value("${stripe.webhooks.secret}")
    private String endpointSecret;

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {

        try {
            // Stripe의 시그니처 확인
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            // 이벤트 처리
            switch (event.getType()) {
                case "payment_intent.succeeded":
                    PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
                    System.out.println("PaymentIntent succeeded: " + paymentIntent.getId());
                    break;

                case "charge.succeeded":
                    Charge charge = (Charge) event.getDataObjectDeserializer().getObject().orElse(null);
                    System.out.println("Charge succeeded: " + charge.getId());
                    break;

                default:
                    System.out.println("Unhandled event type: " + event.getType());
            }

            return ResponseEntity.ok("Webhook processed successfully");
        } catch (SignatureVerificationException e) {
            // 시그니처 검증 실패
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signature verification failed");
        }
    }
}
