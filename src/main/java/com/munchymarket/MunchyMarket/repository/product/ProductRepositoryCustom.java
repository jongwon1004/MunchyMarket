package com.munchymarket.MunchyMarket.repository.product;

import com.munchymarket.MunchyMarket.dto.ProductDto;
import com.munchymarket.MunchyMarket.dto.RegisteredProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ProductRepositoryCustom {

    RegisteredProductDto findByProductId(Long productId);

    Page<ProductDto> findProductsByCategoryId(Long categoryId, Pageable pageable);
}
