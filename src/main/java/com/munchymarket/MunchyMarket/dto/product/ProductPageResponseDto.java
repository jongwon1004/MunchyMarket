package com.munchymarket.MunchyMarket.dto.product;

import com.munchymarket.MunchyMarket.domain.SortType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
public class ProductPageResponseDto {

    private Page<ProductDto> productsByCategoryId;
    private PageRequest pageRequest;
    private SortType sortType;
}
