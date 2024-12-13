package com.munchymarket.MunchyMarket.repository.review;

import com.munchymarket.MunchyMarket.dto.product.ProductReviewDto;

import java.util.List;

public interface ReviewRepositoryCustom {


    List<ProductReviewDto> getProductReviewsByProductId(Long productId);
}
