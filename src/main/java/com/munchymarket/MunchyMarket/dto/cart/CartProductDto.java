package com.munchymarket.MunchyMarket.dto.cart;

import lombok.*;


/**
 * 회원의 쇼핑카트 목록 화면에 보여질 데이터
 */
@NoArgsConstructor
@Setter @Getter
@ToString
public class CartProductDto {

    private Long productId;
    private String productName;
    private int basePrice;
    private int finalPrice;
    private int quantity;

    public CartProductDto(Long productId, String productName, int basePrice, int finalPrice, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.basePrice = basePrice;
        this.finalPrice = finalPrice;
        this.quantity = quantity;
    }
}

/*
    SELECT p.product_name 상품명,p.base_price 원래가격, p.final_price 최종가격, cp.quantity 주문수량
    FROM carts c
        LEFT JOIN cart_products cp on c.cart_id = cp.cart_id
        LEFT JOIN products p on cp.product_id = p.product_id
    WHERE c.member_id = :memberId;
*/