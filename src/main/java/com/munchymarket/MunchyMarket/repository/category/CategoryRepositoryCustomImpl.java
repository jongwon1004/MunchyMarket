package com.munchymarket.MunchyMarket.repository.category;

import com.munchymarket.MunchyMarket.domain.Category;
import com.munchymarket.MunchyMarket.dto.CategoryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.stream.Collectors;

import static com.munchymarket.MunchyMarket.domain.QCategory.category;


public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {



    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        List<Category> topCategories = queryFactory.selectFrom(category)
                .leftJoin(category.children).fetchJoin()
                .where(category.parent.isNull())
                .fetch();


        return topCategories.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private CategoryDto convertToDto(Category category) {
        List<CategoryDto> childrenDtos = category.getChildren().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new CategoryDto(category.getId(), category.getCategoryName(), childrenDtos);
    }

}
