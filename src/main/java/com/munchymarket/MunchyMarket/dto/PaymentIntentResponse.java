package com.munchymarket.MunchyMarket.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentIntentResponse {

    @Schema(description = "결제 클라이언트 시크릿 키", example = "pi_sample_1ONWjXHelloWorld_secret_JonNs9mn2gQiWonkLoveYU")
    private String clientSecret;

    @Schema(description = "결제 Intent ID", example = "pi_sample_1ONWjXHelloWorld")
    private String pi;
}
