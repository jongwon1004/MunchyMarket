package com.munchymarket.MunchyMarket.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CategoryListDto {
    Long parentCategoryId;
    String parentCategoryName;
    List<ChildCategoryListDto> childCategories = new ArrayList<>();

    public CategoryListDto(Long parentCategoryId, String parentCategoryName) {
        this.parentCategoryId = parentCategoryId;
        this.parentCategoryName = parentCategoryName;
    }
}
