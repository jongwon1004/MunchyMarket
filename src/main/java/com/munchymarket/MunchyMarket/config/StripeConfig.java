package com.munchymarket.MunchyMarket.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

    @Value("${stripe.key.secret}")
    private String apiKey;


    @PostConstruct
    public void init() {
        Stripe.apiKey = apiKey;
    }

}
