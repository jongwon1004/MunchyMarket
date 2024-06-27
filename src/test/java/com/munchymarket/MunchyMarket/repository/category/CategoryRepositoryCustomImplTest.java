package com.munchymarket.MunchyMarket.repository.category;

import com.munchymarket.MunchyMarket.dto.CategoryDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CategoryRepositoryCustomImplTest {

    private static final Logger log = LoggerFactory.getLogger(CategoryRepositoryCustomImplTest.class);
    @Autowired
    CategoryRepository categoryRepository;

    @Test
    void findAllCategories() {
//        List<CategoryDto> allCategories = categoryRepository.findAllCategories();
//        log.info("allCategories: {}", allCategories);
    }



}