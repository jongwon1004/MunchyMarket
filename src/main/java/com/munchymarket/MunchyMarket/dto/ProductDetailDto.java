package com.munchymarket.MunchyMarket.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter @Getter
public class ProductDetailDto {

    RegisteredProductDto product;
    List<?> reviews = new ArrayList<>();
    List<?> inquiries = new ArrayList<>(); // 상품문의 리스트
}
