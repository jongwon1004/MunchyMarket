package com.munchymarket.MunchyMarket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 등록된 상품 정보 DTO
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredProductDto {
    Long productId;
    Long categoryId;
    String productName;
    int basePrice;
    int finalPrice;
    String shortDescription;
    int stock;
    String deliveryDescription;
    Long packagingTypeId;
    String origin;
    String unit;
    String volume;
    String expirationDescription;
    String allergyDescription;
    String guideDescription;
    String mainImage;
    String subImage;
    String productDesTop1;
    String productDesTop2;
    String productDesMain;
    boolean isOnSale;
    int salePercentage;
    boolean isPurchaseStatus;
}
