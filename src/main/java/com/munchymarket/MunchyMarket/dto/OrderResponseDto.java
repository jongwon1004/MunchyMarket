package com.munchymarket.MunchyMarket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderResponseDto {


    @Schema(description = "결제 Intent 정보")
    private PaymentIntentResponse paymentIntent;

    @Schema(description = "응답 메시지", example = "注文が確定されました。")
    private String message;
}
