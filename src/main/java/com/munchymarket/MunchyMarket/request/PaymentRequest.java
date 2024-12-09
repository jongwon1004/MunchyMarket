package com.munchymarket.MunchyMarket.request;

import com.munchymarket.MunchyMarket.domain.Member;
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

    private int amount;
    private String currency;
    private String paymentMethodId;
    private String pi;
    private String receiptEmail;

    private Map<String, String> metadata = new HashMap<>();
}
