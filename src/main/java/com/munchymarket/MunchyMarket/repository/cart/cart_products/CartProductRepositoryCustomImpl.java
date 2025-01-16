package com.munchymarket.MunchyMarket.repository.cart.cart_products;

import com.munchymarket.MunchyMarket.dto.cart.CartProductDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static com.munchymarket.MunchyMarket.domain.QCart.*;
import static com.munchymarket.MunchyMarket.domain.QCartProduct.*;
import static com.munchymarket.MunchyMarket.domain.QProduct.*;

public class CartProductRepositoryCustomImpl implements CartProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    public CartProductRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<CartProductDto> findCartProductByMemberIdToDto(Long memberId) {

        return queryFactory
                .select(
                        Projections.constructor(CartProductDto.class,
                                product.id,
                                product.productName,
                                Expressions.stringTemplate(
                                        "concat('https://storage.googleapis.com/', {0}, '/', {1})", bucketName, product.mainImage.serverFilename
                                ),
                                product.basePrice,
                                product.finalPrice,
                                cartProduct.quantity
                        )
                )
                .from(cartProduct)
                .leftJoin(cartProduct.cart, cart)
                .leftJoin(cartProduct.product, product)
                .where(cart.id.eq(memberId))
                .fetch();

    }
}
