package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.request.PaymentRequest;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    public PaymentIntent createPaymentIntent(PaymentRequest paymentRequest) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", paymentRequest.getAmount());
        params.put("currency", paymentRequest.getCurrency());
        params.put("metadata", paymentRequest.getMetadata());
        params.put("payment_method_types", paymentMethodTypes);

        return PaymentIntent.create(params);
    }


    public PaymentIntent confirmPaymentIntent(String pi) throws StripeException {


        PaymentIntent resource = PaymentIntent.retrieve(pi);

        PaymentIntentConfirmParams params =
                PaymentIntentConfirmParams.builder()
                        .setPaymentMethod("pm_card_visa")
//                        .setReturnUrl("https://www.example.com")
                        .build();

        return resource.confirm(params);
    }
}
