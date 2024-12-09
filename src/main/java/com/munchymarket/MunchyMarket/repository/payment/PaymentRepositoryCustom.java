package com.munchymarket.MunchyMarket.repository.payment;

import com.munchymarket.MunchyMarket.domain.enums.PaymentStatus;

public interface PaymentRepositoryCustom {

    Long updateStatusByStripePaymentIntentId(String stripePaymentIntentId, PaymentStatus paymentStatus);
}
