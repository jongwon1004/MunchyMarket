package com.munchymarket.MunchyMarket.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentDto {

    private String paymentMethodId;
    private String currency;
}
