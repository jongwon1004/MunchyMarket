package com.munchymarket.MunchyMarket.dto;

import lombok.*;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter @Getter
public class ProductDetailDto {

    Map<String, Object> reviews = new HashMap<>();
    RegisteredProductDto product;
}
