package com.munchymarket.MunchyMarket.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor
@Getter @Setter
@ToString
public class PaymentRequest {


    private Long amount;
    private String currency;
    private String paymentMethodId;

    private String pi;
}
