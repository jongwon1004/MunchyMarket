package com.munchymarket.MunchyMarket.repository.payment;

import com.munchymarket.MunchyMarket.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long>, PaymentRepositoryCustom {

}
