package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.domain.Payment;
import com.munchymarket.MunchyMarket.domain.enums.PaymentStatus;
import com.munchymarket.MunchyMarket.dto.orderpay.PaymentDto;
import com.munchymarket.MunchyMarket.repository.payment.PaymentRepository;
import com.munchymarket.MunchyMarket.service.common.CommonEntityService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentConfirmParams;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final CommonEntityService commonLogicsService;


    public Map<String, String> createPaymentIntent(PaymentDto paymentDto, Long memberId, int amount) throws StripeException {
//    public Map<String, String> createPaymentIntent(PaymentRequest paymentRequest) throws StripeException {
        List<String> paymentMethodTypes = new ArrayList<>();
        paymentMethodTypes.add("card");

        Map<String, Object> params = new HashMap<>();
        params.put("amount", amount);
        params.put("currency", paymentDto.getCurrency());
        params.put("payment_method_types", paymentMethodTypes);

        Map<String, String> response = new HashMap<>();
        PaymentIntent paymentIntent = PaymentIntent.create(params); // paymentIntent 생성
        log.info("paymentIntent: {}", paymentIntent);
        log.info("paymentIntent.getId(): {}", paymentIntent.getId());

        response.put("clientSecret", paymentIntent.getClientSecret());
        response.put("pi", paymentIntent.getId());

        Payment payment = convertToEntity(paymentDto, memberId, paymentIntent);
        paymentRepository.save(payment);

        return response;
    }

    public Payment convertToEntity(PaymentDto paymentDto, Long memberId, PaymentIntent paymentIntent) {
        return Payment.builder()
                .amount(paymentIntent.getAmount())
                .currency(paymentIntent.getCurrency())
                .paymentMethod(paymentDto.getPaymentMethodId())
                .stripePaymentIntentId(paymentIntent.getId())
                .memberId(commonLogicsService.findMemberById(memberId))
                .status(PaymentStatus.PENDING)
                .build();
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
