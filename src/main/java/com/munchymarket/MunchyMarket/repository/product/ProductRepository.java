package com.munchymarket.MunchyMarket.repository.product;

import com.munchymarket.MunchyMarket.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

//    List<Product> findAllById(List<Long> productIds);

}
