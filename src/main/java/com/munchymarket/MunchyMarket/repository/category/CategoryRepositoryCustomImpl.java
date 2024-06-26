package com.munchymarket.MunchyMarket.repository.category;

import com.munchymarket.MunchyMarket.dto.CategoryListDto;
import com.munchymarket.MunchyMarket.dto.ChildCategoryListDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.munchymarket.MunchyMarket.domain.QCategory.category;

public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CategoryRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public List<CategoryListDto> findAllCategories() {
        return queryFactory.select(
                        Projections.constructor(CategoryListDto.class,
                                category.id,
                                category.categoryName)
                )
                .from(category)
                .fetch();
    }

    @Override
    public List<ChildCategoryListDto> findSubCategoriesByParentId(Long parentCategoryId) {
        return queryFactory.select(
                        Projections.constructor(ChildCategoryListDto.class,
                                category.id,
                                category.categoryName)
                )
                .from(category)
                .where(category.parent.id.eq(parentCategoryId))
                .fetch();
    }
}
