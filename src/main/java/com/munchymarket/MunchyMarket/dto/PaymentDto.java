package com.munchymarket.MunchyMarket.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDto {

    @Schema(description = "결제 수단 ID", example = "pm_1QJONGW1ONONX1Kx004pQmAd")
    private String paymentMethodId;

    @Schema(description = "결제 통화", example = "jpy")
    private String currency;
}
