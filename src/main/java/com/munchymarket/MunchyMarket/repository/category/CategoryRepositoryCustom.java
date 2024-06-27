package com.munchymarket.MunchyMarket.repository.category;

import com.munchymarket.MunchyMarket.dto.CategoryDto;

import java.util.List;

public interface CategoryRepositoryCustom {


    List<CategoryDto> findAllCategories();
}
