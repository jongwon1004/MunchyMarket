package com.munchymarket.MunchyMarket.repository.category;

import com.munchymarket.MunchyMarket.domain.Category;
import com.munchymarket.MunchyMarket.domain.QCategory;
import com.munchymarket.MunchyMarket.dto.CategoryDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.*;
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

    @Override
    public Optional<CategoryDto> findByIdWithChildren(Long categoryId) {

        QCategory childCategory = new QCategory("childCategory");

        List<Category> categories = queryFactory.selectFrom(category)
                .leftJoin(category.children, childCategory).fetchJoin()
                .where(category.id.eq(categoryId))
                .fetch();

        if (categories.isEmpty()) {
            return Optional.empty();
        }

        Map<Long, CategoryDto> dtoMap = new HashMap<>();
        for (Category category : categories) {
            // computeIfAbsent 는 Map에 지정된 키가 없거나 연관된 값이 null인 경우에만 새로운 값을 계산하고 이를 맵에 저장함
            // categoryId 가 903 인경우 -> dtoMap 에는 아직 903 이 없으므로 새로운 CategoryDto 를 생성하여 dtoMap 에 저장
            CategoryDto dto = dtoMap.computeIfAbsent(category.getId(), id -> new CategoryDto(category.getId(), category.getCategoryName(), new ArrayList<>()));

            // 자식 카테고리를 순회하면서 dtoMap 에 저장된 CategoryDto 를 찾아서 dto 의 children 에 추가
            for (Category child : category.getChildren()) {
                CategoryDto childDto = dtoMap.computeIfAbsent(child.getId(), id -> new CategoryDto(child.getId(), child.getCategoryName(), new ArrayList<>()));
                dto.getChildren().add(childDto);
            }
        }
        System.out.println("dtoMap.get(categoryId) = " + dtoMap.get(categoryId));


        return Optional.ofNullable(dtoMap.get(categoryId));
    }
}
