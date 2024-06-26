package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.dto.CategoryListDto;
import com.munchymarket.MunchyMarket.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryListDto> getCategories() {
        List<CategoryListDto> allCategories = categoryRepository.findAllCategories();
        log.info("allCategories: {}", allCategories);

        allCategories.forEach(category -> {
            category.setChildCategories(categoryRepository.findSubCategoriesByParentId(category.getParentCategoryId()));
        });

        return allCategories;
    }

}
