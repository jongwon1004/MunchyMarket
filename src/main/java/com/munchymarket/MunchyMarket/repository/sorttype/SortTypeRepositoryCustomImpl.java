package com.munchymarket.MunchyMarket.repository.sorttype;

import com.munchymarket.MunchyMarket.dto.product.SortTypeDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.munchymarket.MunchyMarket.domain.QSortType.sortType;

public class SortTypeRepositoryCustomImpl implements SortTypeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public SortTypeRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<SortTypeDto> getSortTypes() {
        return queryFactory
                .select(
                        Projections.constructor(SortTypeDto.class,
                                sortType.id.as("sortTypeId"),
                                sortType.displayName
                        )
                )
                .from(sortType)
                .fetch();
    }
}
