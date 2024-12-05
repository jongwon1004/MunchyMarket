package com.munchymarket.MunchyMarket.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor
@Getter @Setter
@ToString
public class PaymentRequest {


    private Long customer;
    private Long amount;
    private String currency;
    private String paymentMethodId;
    private String pi;

    private Map<String, String> metadata = new HashMap<>();
}
