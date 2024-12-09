package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.dto.OrderPaymentRequestDto;
import com.munchymarket.MunchyMarket.request.PaymentRequest;
import com.munchymarket.MunchyMarket.service.OrderService;
import com.munchymarket.MunchyMarket.service.PaymentService;
import com.munchymarket.MunchyMarket.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    private final JWTUtil jwtUtil;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderPaymentRequestDto orderPaymentRequestDto, @RequestHeader("Authorization") String tk) {
        log.info("orderPaymentRequestDto: {}", orderPaymentRequestDto);

        if (!jwtUtil.getId(tk).equals(orderPaymentRequestDto.getMemberId())) {
            return ResponseEntity.badRequest().body("jwt tokenとmetadataのmemberIdが一致しません。");
        }
        return ResponseEntity.ok().build();
    }
}
