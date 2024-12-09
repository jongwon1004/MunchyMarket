package com.munchymarket.MunchyMarket.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderPaymentRequestDto {

    private Long memberId;
    private Long couponId; // nullable
    private OrderDto order;
    private PaymentDto payment;

    /**
     * {
     *   "memberId": 3,
     *   "couponId": 1,
     *   "order": {
     *     "products": [
     *       { "productId": 1, "quantity": 2 }, == 1000
     *       { "productId": 2, "quantity": 1 }, == 300
     *       { "productId": 13, "quantity": 3 } == 1350
     *     ]
     *   },
     *   "payment": {
     *     "paymentMethodId": "pm_123456789",
     *     "currency": "jpy",
     *   }
     * }
     */
}
