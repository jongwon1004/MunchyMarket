package com.munchymarket.MunchyMarket.dto;

import lombok.*;
import org.springframework.data.domain.Slice;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class ProductListResponseDto {

    private String sortedType;
    private Slice<ProductDto> products;
    private long totalElements;
    private int totalPages;
}
