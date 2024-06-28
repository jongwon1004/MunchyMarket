package com.munchymarket.MunchyMarket.repository.category;

import com.munchymarket.MunchyMarket.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryRepositoryCustom {


    List<CategoryDto> findAllCategories();
    Optional<CategoryDto> findByIdWithChildren(Long categoryId);
}
