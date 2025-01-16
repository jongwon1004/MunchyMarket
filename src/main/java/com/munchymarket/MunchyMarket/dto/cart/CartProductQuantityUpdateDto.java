package com.munchymarket.MunchyMarket.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartProductQuantityUpdateDto {
    private Long productId;
    private int quantity;
    private LocalDateTime lastModifiedDate;
}
