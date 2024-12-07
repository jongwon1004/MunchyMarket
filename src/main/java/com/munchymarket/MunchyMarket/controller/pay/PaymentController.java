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

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final JWTUtil jwtUtil;

//    @PostMapping("/jwt/jwt-test")
//    public ResponseEntity<String> jwtTest(@RequestHeader("Authorization") String token) {
//        log.info("token ={}", token);
//        Long id = jwtUtil.getId(token);
//        log.info("id = {}", id);
//        return ResponseEntity.ok("jwtTest");
//    }

    @PostMapping("/pi-create")
    public ResponseEntity<String> createPaymentIntent(@RequestBody PaymentRequest paymentRequest)
            throws StripeException {


        log.info("paymentRequest = {}", paymentRequest);

        PaymentIntent paymentIntent = paymentService.createPaymentIntent(paymentRequest);
        String paymentStr = paymentIntent.toJson();

        return ResponseEntity.ok(paymentStr);
    }

    @PostMapping("/pi-confirm")
    public ResponseEntity<?> confirmPaymentIntent(@RequestBody PaymentRequest paymentRequest)
            throws StripeException {

        PaymentIntent paymentIntent = paymentService.confirmPaymentIntent(paymentRequest.getPi());
        String paymentStr = paymentIntent.toJson();

        return ResponseEntity.ok(paymentStr);
    }


}
