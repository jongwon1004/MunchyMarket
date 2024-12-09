package com.munchymarket.MunchyMarket.repository.coupon;

import com.munchymarket.MunchyMarket.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
