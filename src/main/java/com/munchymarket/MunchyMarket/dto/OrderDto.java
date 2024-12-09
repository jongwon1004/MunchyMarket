package com.munchymarket.MunchyMarket.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {
    private List<ProductIdAndQuantityDto> products;
}
