package com.munchymarket.MunchyMarket.repository.cart;

import com.munchymarket.MunchyMarket.domain.Product;
import com.munchymarket.MunchyMarket.domain.QCartProduct;
import com.munchymarket.MunchyMarket.dto.ProductIdAndQuantityDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class CartRepositoryCustomImpl implements CartRepositoryCustom {


    private final JPAQueryFactory queryFactory;

    public CartRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void addProductToCart(Product product, int quantity) {


    }

}
