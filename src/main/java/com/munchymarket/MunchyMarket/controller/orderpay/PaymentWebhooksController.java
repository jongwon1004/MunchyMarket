package com.munchymarket.MunchyMarket.controller.orderpay;

import com.munchymarket.MunchyMarket.domain.enums.PaymentStatus;
import com.munchymarket.MunchyMarket.repository.payment.PaymentRepository;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Charge;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/webhooks")
public class PaymentWebhooksController {

    private final PaymentRepository paymentRepository;


    @Value("${stripe.webhooks.secret}")
    private String endpointSecret;

    @PostMapping("/stripe")
    public ResponseEntity<String> handleStripeWebhook(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {

        log.info("payload = {}", payload);
        log.info("sigHeader = {}", sigHeader);

        try {
            // Stripe의 시그니처 확인
            Event event = Webhook.constructEvent(payload, sigHeader, endpointSecret);

            // 이벤트 처리
            switch (event.getType()) {

                case "payment_intent.created":
                    PaymentIntent createdPaymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
                    log.info("PaymentIntent created = {}", createdPaymentIntent.getId());
                    break;

                case "payment_intent.succeeded":
                    PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
                    log.info("PaymentIntent succeeded = {}", paymentIntent.getId());
                    paymentRepository.updateStatusByStripePaymentIntentId(paymentIntent.getId(), PaymentStatus.SUCCEEDED);
                    break;

                case "charge.succeeded":
                    Charge charge = (Charge) event.getDataObjectDeserializer().getObject().orElse(null);
                    log.info("Charge succeeded = {}", charge.getId());
                    break;

                case "payment_intent.payment_failed":
                    PaymentIntent paymentIntentFailed = (PaymentIntent) event.getDataObjectDeserializer().getObject().orElse(null);
                    log.info("PaymentIntent failed = {}", paymentIntentFailed.getId());
                    paymentRepository.updateStatusByStripePaymentIntentId(paymentIntentFailed.getId(), PaymentStatus.FAILED);
                    break;

                default:
                    log.info("Unhandled event type: {}", event.getType());
            }

            return ResponseEntity.ok("Webhook processed successfully");
        } catch (SignatureVerificationException e) {
            // 시그니처 검증 실패
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Signature verification failed");
        }
    }
}
