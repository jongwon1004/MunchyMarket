package com.munchymarket.MunchyMarket.repository.category;

import com.munchymarket.MunchyMarket.domain.Category;
import com.munchymarket.MunchyMarket.dto.CategoryDto;
import com.munchymarket.MunchyMarket.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CategoryRepositoryCustomImplTest {

    private static final Logger log = LoggerFactory.getLogger(CategoryRepositoryCustomImplTest.class);
    @Autowired
    CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;




}