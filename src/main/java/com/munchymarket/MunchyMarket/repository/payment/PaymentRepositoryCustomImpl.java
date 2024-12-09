package com.munchymarket.MunchyMarket.repository.payment;


import com.munchymarket.MunchyMarket.request.PaymentRequest;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class PaymentRepositoryCustomImpl implements PaymentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PaymentRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public Long createPayment(PaymentRequest paymentRequest) {

        return null;
    }
}
