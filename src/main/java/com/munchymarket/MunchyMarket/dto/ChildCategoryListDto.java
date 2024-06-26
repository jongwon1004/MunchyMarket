package com.munchymarket.MunchyMarket.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChildCategoryListDto {
    Long childCategoryId;
    String childCategoryName;

    public ChildCategoryListDto(Long childCategoryId, String childCategoryName) {
        this.childCategoryId = childCategoryId;
        this.childCategoryName = childCategoryName;
    }
}
