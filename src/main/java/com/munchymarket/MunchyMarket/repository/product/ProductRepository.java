package com.munchymarket.MunchyMarket.repository.product;

import com.munchymarket.MunchyMarket.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
}
