package com.munchymarket.MunchyMarket.controller.pay;

import com.munchymarket.MunchyMarket.request.PaymentRequest;
import com.munchymarket.MunchyMarket.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pi-create")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentRequest paymentRequest)
            throws StripeException {

        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentRequest);
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/pi-confirm")
    public ResponseEntity<?> confirmPaymentIntent(@RequestBody PaymentRequest paymentRequest)
            throws StripeException {

        PaymentIntent paymentIntent = paymentService.confirmPaymentIntent(paymentRequest.getPi());
        String paymentStr = paymentIntent.toJson();

        return new ResponseEntity<>(paymentStr, HttpStatus.OK);
    }


}
