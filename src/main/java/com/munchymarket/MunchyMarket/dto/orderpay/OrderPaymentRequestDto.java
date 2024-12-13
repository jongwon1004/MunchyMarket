package com.munchymarket.MunchyMarket.dto.orderpay;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderPaymentRequestDto {

    @Schema(description = "쿠폰 ID (nullable)", example = "3")
    private Long couponId;

    @Schema(description = "주문 정보")
    private OrderDto order;

    @Schema(description = "결제 정보")
    private PaymentDto payment;
}


    /**
     * {
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
