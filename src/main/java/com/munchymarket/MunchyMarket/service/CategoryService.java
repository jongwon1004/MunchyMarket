package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.dto.CategoryDto;
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


    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAllCategories();
    }



}
