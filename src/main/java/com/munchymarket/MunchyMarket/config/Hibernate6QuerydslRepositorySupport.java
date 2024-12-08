package com.munchymarket.MunchyMarket.config;

import com.querydsl.core.types.EntityPath;
import com.querydsl.jpa.JPQLQuery;
import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public abstract class Hibernate6QuerydslRepositorySupport extends QuerydslRepositorySupport {

    private Hibernate6Querydsl querydsl;

    public Hibernate6QuerydslRepositorySupport(Class<?> domainClass) {
        super(domainClass);
    }

    @Override
    protected Querydsl getQuerydsl() {
        if (null == querydsl && getEntityManager() != null) {
            this.querydsl = new Hibernate6Querydsl(getEntityManager(), getBuilder());
        }
        return this.querydsl;
    }

    @Nonnull
    private Querydsl getRequiredQuerydsl() {
        if (getQuerydsl() == null) {
            throw new IllegalStateException("Querydsl is null");
        }
        return getQuerydsl();
    }

    @Override
    protected JPQLQuery<Object> from(EntityPath<?>... paths) {
        return getRequiredQuerydsl().createQuery(paths);
    }

    @Override
    protected <T> JPQLQuery<T> from(EntityPath<T> path) {
        return getRequiredQuerydsl().createQuery(path).select(path);
    }
}
