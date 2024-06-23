package com.munchymarket.MunchyMarket.repository;

import com.munchymarket.MunchyMarket.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
