package com.munchymarket.MunchyMarket.repository.product;

import com.munchymarket.MunchyMarket.dto.product.ProductDto;
import com.munchymarket.MunchyMarket.dto.product.RegisteredProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    RegisteredProductDto findByProductId(Long productId);

    Page<ProductDto> findProductsByCategoryId(Long categoryId, Pageable pageable);
}
