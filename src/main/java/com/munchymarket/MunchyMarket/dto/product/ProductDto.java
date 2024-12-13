package com.munchymarket.MunchyMarket.dto.product;

import lombok.*;

/**
 * 상품 리스트 화면에서 쓸 DTO
 */
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
    private int salePercentage;
    private Boolean isPurchaseStatus;

}
