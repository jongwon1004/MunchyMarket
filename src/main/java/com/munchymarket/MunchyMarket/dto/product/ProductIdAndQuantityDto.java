package com.munchymarket.MunchyMarket.dto.product;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductIdAndQuantityDto {

    private Long productId;
    private int quantity;
}
