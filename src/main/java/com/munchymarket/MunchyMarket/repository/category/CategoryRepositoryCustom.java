package com.munchymarket.MunchyMarket.repository.category;

import com.munchymarket.MunchyMarket.dto.CategoryDto;
import com.munchymarket.MunchyMarket.dto.ParentCategoryDto;
import com.munchymarket.MunchyMarket.dto.ChildCategoryDto;

import java.util.List;

public interface CategoryRepositoryCustom {


    List<CategoryDto> findAllCategories();
}
