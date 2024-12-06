package com.munchymarket.MunchyMarket.domain;

import com.munchymarket.MunchyMarket.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Table(name = "payments")
@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    @Column(name = "stripe_payment_intent_id", nullable = false, length = 50)
    private String stripePaymentIntentId;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false, length = 10)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private PaymentStatus status;

    @Column(name = "payment_method", nullable = false, length = 50)
    private String paymentMethod;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "recipt_email", length = 100)
    private String receiptEmail;


}
