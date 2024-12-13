package com.munchymarket.MunchyMarket.dto.product;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter @Getter
public class ProductDetailDto {

    RegisteredProductDto product;
    List<ProductReviewDto> reviews = new ArrayList<>();
    List<?> inquiries = new ArrayList<>(); // 상품문의 리스트
}
