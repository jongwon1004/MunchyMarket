package com.munchymarket.MunchyMarket.repository.review;

import com.munchymarket.MunchyMarket.dto.ProductReviewDto;
import com.munchymarket.MunchyMarket.dto.ReviewImageResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.munchymarket.MunchyMarket.domain.QImage.image;
import static com.munchymarket.MunchyMarket.domain.QReview.review;
import static com.munchymarket.MunchyMarket.domain.QReviewImage.reviewImage;

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
            return queryFactory.select(
                            Projections.fields(ProductReviewDto.class,
                                    review.id.as("reviewId"),
                                    review.member.id.as("memberId"),
                                    review.member.name.as("memberName"),
                                    review.rating.as("rating"),
                                    review.content.as("content"),
                                    review.createdDate.as("createdDate"),
                                    review.lastModifiedDate.as("lastModifiedDate"),
                                    Expressions.stringTemplate("GROUP_CONCAT({0})", reviewImage.image.serverFilename).as("reviewImagesRaw") // 이미지 파일명 리스트를 문자열로

                            )
                    )
                    .from(review)
                    .leftJoin(review.reviewImages, reviewImage)
                    .leftJoin(reviewImage.image, image)
                    .where(review.product.id.eq(productId))
                    .groupBy(review.id)
                    .fetch();
    }

}
