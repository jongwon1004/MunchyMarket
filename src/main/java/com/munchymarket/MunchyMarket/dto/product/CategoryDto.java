package com.munchymarket.MunchyMarket.dto.product;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CategoryDto {
    private Long id;
    private String categoryName;
    private List<CategoryDto> children;
}
