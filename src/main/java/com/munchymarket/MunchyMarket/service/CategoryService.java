package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.domain.Category;
import com.munchymarket.MunchyMarket.dto.product.CategoryDto;
import com.munchymarket.MunchyMarket.repository.category.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAllCategories();
    }

    public CategoryDto getCategoryWithChildren(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found")); // EntityNotFoundException : JPA에서 제공하는 예외
        Category rootCategory = (category.getParent() == null) ? category : category.getParent();
        return convertToDto(rootCategory);
    }

    private CategoryDto convertToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setCategoryName(category.getCategoryName());
        dto.setChildren(category.getChildren().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
        return dto;
    }

}
