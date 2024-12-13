package com.munchymarket.MunchyMarket.dto.orderpay;

import com.munchymarket.MunchyMarket.dto.product.ProductIdAndQuantityDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {

    @Schema(description = "주문 상품 리스트", example = "[{ \"productId\": 1, \"quantity\": 2 }, { \"productId\": 2, \"quantity\": 1 }, { \"productId\": 13, \"quantity\": 3 }]")
    private List<ProductIdAndQuantityDto> products;
}
