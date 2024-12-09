package com.munchymarket.MunchyMarket.repository;

import com.munchymarket.MunchyMarket.domain.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
