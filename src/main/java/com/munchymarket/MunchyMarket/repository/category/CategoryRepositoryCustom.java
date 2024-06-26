package com.munchymarket.MunchyMarket.repository.category;

import com.munchymarket.MunchyMarket.dto.CategoryListDto;
import com.munchymarket.MunchyMarket.dto.ChildCategoryListDto;

import java.util.List;

public interface CategoryRepositoryCustom {

    public List<CategoryListDto> findAllCategories();

    List<ChildCategoryListDto> findSubCategoriesByParentId(Long parentCategoryId);
}
