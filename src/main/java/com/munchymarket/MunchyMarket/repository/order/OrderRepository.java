package com.munchymarket.MunchyMarket.repository.order;

import com.munchymarket.MunchyMarket.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
}
