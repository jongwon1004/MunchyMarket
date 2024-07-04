package com.munchymarket.MunchyMarket.repository.product;

import com.munchymarket.MunchyMarket.domain.Product;
import com.munchymarket.MunchyMarket.domain.QCategory;
import com.munchymarket.MunchyMarket.domain.QProduct;
import com.munchymarket.MunchyMarket.dto.ProductDto;
import com.munchymarket.MunchyMarket.dto.RegisteredProductDto;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;

import java.util.List;

import static com.munchymarket.MunchyMarket.domain.QProduct.product;

public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private static final Logger log = LoggerFactory.getLogger(ProductRepositoryCustomImpl.class);
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

    @Override
    public Page<ProductDto> findProductsByCategoryId(Long categoryId, Pageable pageable) {

        List<ProductDto> productDtos = queryFactory
                .select(
                        Projections.constructor(ProductDto.class,
                                product.id.as("productId"),
                                product.productName,
                                product.basePrice,
                                product.shortDescription,
                                product.stock,
                                Expressions.stringTemplate("concat('https://storage.googleapis.com/', {0}, '/', {1})", bucketName, product.mainImage.serverFilename),
                                product.isOnSale,
                                product.salePercentage,
                                product.isPurchaseStatus)
                )
                .from(product)
                .where(product.category.id.eq(categoryId)
                        .or(product.category.parent.id.eq(categoryId)))
                .orderBy(getOrderSpecifiers(pageable.getSort()))  // Sort 적용
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1) // + 1 해서 다음 페이지가 있는지 확인하는데, 다음 페이지가 없으면 list 의 size() 가 pageSize() 만큼 가져옴 -> 밑에 조건식 false
                .fetch();

        // 등록된 상품은 10개, productDtos.size() = 10, pageable.getPageSize() = 9
        log.info("productDtos.size() = {}", productDtos.size());
        log.info("pageable.getPageSize() = {}", pageable.getPageSize());

        // 등록된 상품은 10개고, 각 페이지당 9 개 상품을 보여줄 수 있으니까, hasNext = true
        boolean hasNext = productDtos.size() > pageable.getPageSize();

        // hasNext = true -> 다음 페이지가 존재함
        // `Slice`를 반환하기 위해 `Pageable`을 `Page`로 변환
        if (hasNext) {
            productDtos.remove(productDtos.size() - 1);
        }


        /*
            Slice 와 Page 를 동시에 사용하기에 카운트 쿼리도 날려준다.
            Slice 만 쓰면 카운트 쿼리는 필요없음!
         */
        Long total = queryFactory
                .select(Wildcard.count)
                .from(product)
                .where(product.category.id.eq(categoryId)
                        .or(product.category.parent.id.eq(categoryId)))
                .fetchOne();

        long totalElements = (total != null) ? total : 0L;

        return new PageImpl<>(productDtos, pageable, totalElements);
    }

    /**
     * QueryDSL은 정렬 조건을 OrderSpecifier 객체로 표현한다. Spring Data JPA 의 Sort 객체를 직접 사용할 수 없기 때문에
     * 이를 QueryDSL 의 OrderSpecifier 로 변환해야한다.
     * OrderSpecifier 는 QueryDSL 에서 정렬을 정의하는데 사용되고, 정렬할 필드와 정렬 방향(ASC, DESC)를 포함한다.
     */
    private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {

        /**
         * 동작원리
         * 1. sort.stream() 으로 Sort 객체에 포함된 Order 객체들을 Stream 으로 변환한다.
         * 2. map() 으로 Order 객체를 OrderSpecifier 로 변환한다.
         *    -> order.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC
         *    -> 정렬 방향을 결정. isAscending() 메서드는 정렬 방향이 ASC 인지 확인한다.
         *
         *    -> Expressions.path(Product.class, QProduct.product, order.getProperty())
         *    -> 정렬할 필드를 나타내는 표현식을 생성. order.getProperty() 는 정렬할 필드 이름. 예를들면 productName 이나 basePrice 같은 필드 이름
         *    -> 필드이름은 Product 엔티티의 필드명으로 조회
         */

        return sort.stream()
                .map(order -> {
                    // order.getProperty() 값 로그 출력
                    // log.info("order.getProperty() = {}", order.getProperty());
                    return new OrderSpecifier(
                            order.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC,
                            Expressions.path(Product.class, QProduct.product, order.getProperty()));
                })
                .toArray(OrderSpecifier[]::new);
    }
}
