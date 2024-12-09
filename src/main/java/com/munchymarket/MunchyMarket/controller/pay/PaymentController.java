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

import java.util.Collections;
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
    public ResponseEntity<Map<String, String>> createPaymentIntent(@RequestBody PaymentRequest paymentRequest, @RequestHeader("Authorization") String tk)
            throws StripeException {


        log.info("paymentRequest: {}", paymentRequest);
        Long memberId = Long.valueOf(paymentRequest.getMetadata().get("memberId"));
        log.info("memberId: {}", memberId);

        Long id = jwtUtil.getId(tk);
        log.info("id: {}", id);

        if (!jwtUtil.getId(tk).equals(Long.valueOf(paymentRequest.getMetadata().get("memberId")))) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "jwt tokenとmetadataのmemberIdが一致しません。"));
        }

        Map<String, String> response = paymentService.createPaymentIntent(paymentRequest);

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
