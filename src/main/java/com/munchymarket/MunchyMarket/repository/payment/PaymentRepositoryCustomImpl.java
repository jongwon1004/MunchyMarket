package com.munchymarket.MunchyMarket.repository.payment;


import com.munchymarket.MunchyMarket.domain.Payment;
import com.munchymarket.MunchyMarket.domain.QPayment;
import com.munchymarket.MunchyMarket.domain.enums.PaymentStatus;
import com.munchymarket.MunchyMarket.request.PaymentRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import static com.munchymarket.MunchyMarket.domain.QPayment.*;

public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PaymentRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    @Transactional
    public Long updateStatusByStripePaymentIntentId(String stripePaymentIntentId, PaymentStatus paymentStatus) {
        Payment selectedPayment = queryFactory
                .selectFrom(payment)
                .where(payment.stripePaymentIntentId.eq(stripePaymentIntentId))
                .fetchOne();

        if (selectedPayment == null) {
            throw new EntityNotFoundException("Payment not found for Stripe Payment Intent ID: " + stripePaymentIntentId);
        }

        selectedPayment.changeStatus(paymentStatus);

        return selectedPayment.getPaymentId();
    }
}
