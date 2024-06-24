package com.munchymarket.MunchyMarket.repository.product;

import com.munchymarket.MunchyMarket.dto.RegisteredProductDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Value;

import static com.munchymarket.MunchyMarket.domain.QProduct.product;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ProductRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Override
    public RegisteredProductDto findByProductId(Long productId) {
        return queryFactory.select(
                        Projections.constructor(RegisteredProductDto.class,
                                product.id,
                                product.category.id,
                                product.productName,
                                product.basePrice,
                                product.shortDescription,
                                product.stock,
                                product.deliveryDescription,
                                product.packagingType.id,
                                product.origin,
                                product.unit,
                                product.volume,
                                product.expirationDescription,
                                product.allergyDescription,
                                product.guideDescription,
                                Expressions.stringTemplate("concat('https://storage.googleapis.com/', {0}, '/', {1})", bucketName, product.mainImage.serverFilename),
                                Expressions.stringTemplate("concat('https://storage.googleapis.com/', {0}, '/', {1})", bucketName, product.subImage.serverFilename),
                                product.productDesTop1,
                                product.productDesTop2,
                                product.productDesMain,
                                product.isOnSale,
                                product.salePercentage,
                                product.isPurchaseStatus
                        )
                )
                .from(product)
                .where(product.id.eq(productId))
                .fetchOne();
    }
}
