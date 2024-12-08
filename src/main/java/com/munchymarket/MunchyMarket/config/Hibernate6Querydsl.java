package com.munchymarket.MunchyMarket.config;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.data.jpa.repository.support.Querydsl;

class Hibernate6Querydsl extends Querydsl {
    private final EntityManager entityManager;

    public Hibernate6Querydsl(EntityManager entityManager, PathBuilder<?> builder) {
        super(entityManager, builder);
        this.entityManager = entityManager;
    }

    @Override
    public <T> AbstractJPAQuery<T, JPAQuery<T>> createQuery() {
        return new JPAQuery<>(entityManager, JPQLTemplates.DEFAULT); // Hibernate 호환 JPQL 템플릿 사용
    }
}
