package com.munchymarket.MunchyMarket.controller.pay;

import com.munchymarket.MunchyMarket.request.PaymentRequest;
import com.munchymarket.MunchyMarket.service.PaymentService;
import com.munchymarket.MunchyMarket.utils.JWTUtil;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final JWTUtil jwtUtil;


    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody PaymentRequest paymentRequest)
            throws StripeException {

        log.info("paymentRequest = {}", paymentRequest);
        Map<String, String> response = new HashMap<>();

        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentRequest);
        log.info("paymentIntent.getClientSecret() = {}", paymentIntent.getClientSecret());
        log.info("paymentIntent.getId() = {}", paymentIntent.getId());

        response.put("clientSecret", paymentIntent.getClientSecret());
        response.put("pi", paymentIntent.getId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPaymentIntent(@RequestBody PaymentRequest paymentRequest)
            throws StripeException {

        PaymentIntent paymentIntent = paymentService.confirmPaymentIntent(paymentRequest.getPi());
        String paymentStr = paymentIntent.toJson();

        return ResponseEntity.ok(paymentStr);
    }


}
