package com.munchymarket.MunchyMarket.repository.category;

import com.munchymarket.MunchyMarket.dto.CategoryListDto;

import java.util.List;

public interface CategoryRepositoryCustom {

    public List<CategoryListDto> findAllCategories();
}
