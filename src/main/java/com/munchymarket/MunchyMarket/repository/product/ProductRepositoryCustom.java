package com.munchymarket.MunchyMarket.repository.product;

import com.munchymarket.MunchyMarket.dto.RegisteredProductDto;

public interface ProductRepositoryCustom {

    RegisteredProductDto findByProductId(Long productId);
}
