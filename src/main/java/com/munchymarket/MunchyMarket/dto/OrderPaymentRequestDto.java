package com.munchymarket.MunchyMarket.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderPaymentRequestDto {

    private Long memberId;
    private OrderDto order;
    private PaymentDto payment;

    /**
     * {
     *   "memberId": 3,
     *   "order": {
     *     "products": [
     *       { "productId": 1, "quantity": 2 },
     *       { "productId": 2, "quantity": 1 },
     *       { "productId": 13, "quantity": 3 }
     *     ]
     *   },
     *   "payment": {
     *     "paymentMethodId": "pm_123456789",
     *     "currency": "jpy",
     *   }
     * }
     */
}
