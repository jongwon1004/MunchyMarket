package com.munchymarket.MunchyMarket.dto;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class ProductDto {

    private Long productId;
    private String productName;
    private int basePrice;
    private String shortDescription;
    private int stock;
    private String mainImage;
    private Boolean isOnSale;
    private BigDecimal salePercentage;
    private Boolean isPurchaseStatus;

}
