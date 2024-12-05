package com.munchymarket.MunchyMarket.domain;

import com.munchymarket.MunchyMarket.domain.base.TimeBaseEntity;
import com.munchymarket.MunchyMarket.domain.enums.CouponType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Coupon extends TimeBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_type", nullable = false)
    private CouponType couponType;

    @Column(name = "discount_value", nullable = false)
    private int discountValue;

    @Column(name = "min_order_value", nullable = false)
    private int minOrderValue;

    @Column(name = "max_discount_value")
    private int maxDiscountValue;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;


}
