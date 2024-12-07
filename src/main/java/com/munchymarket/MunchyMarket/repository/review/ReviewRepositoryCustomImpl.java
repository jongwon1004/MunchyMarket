package com.munchymarket.MunchyMarket.repository.review;

import com.munchymarket.MunchyMarket.dto.ProductReviewDto;
import com.munchymarket.MunchyMarket.dto.ReviewImageResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

import static com.munchymarket.MunchyMarket.domain.QImage.image;
import static com.munchymarket.MunchyMarket.domain.QReview.review;
import static com.munchymarket.MunchyMarket.domain.QReviewImage.reviewImage;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Slf4j
public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {


    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;



    @Override
    @Transactional
    public List<ProductReviewDto> getProductReviewsByProductId(Long productId) {

        List<ProductReviewDto> transform = queryFactory.from(review)
                .leftJoin(review.reviewImages, reviewImage)
                .leftJoin(reviewImage.image, image)
                .where(review.product.id.eq(productId))
                .orderBy(review.id.desc())
                .transform(groupBy(review.id).list( // 리뷰 ID로 그룹화
                        Projections.constructor(ProductReviewDto.class,
                                review.id,
                                review.member.id,
                                review.member.name,
                                review.rating,
                                review.content,
                                list( // 이미지 데이터를 리스트로 변환
                                        Projections.constructor(ReviewImageResponse.class,
                                                image.id,
                                                Expressions.stringTemplate(
                                                        "concat('https://storage.googleapis.com/', {0}, '/', {1})", "munchymarket-bucket", image.serverFilename
                                                )
                                        )
                                ),
                                review.createdDate,
                                review.lastModifiedDate
                        )
                ));
        log.info("transform = " + transform);
        return null;
    }




}
