package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.dto.product.ProductReviewDto;
import com.munchymarket.MunchyMarket.dto.product.ReviewImageResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.munchymarket.MunchyMarket.domain.QImage.image;
import static com.munchymarket.MunchyMarket.domain.QReview.review;
import static com.munchymarket.MunchyMarket.domain.QReviewImage.reviewImage;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;


@SpringBootTest
@Transactional
@Commit
class ReviewServiceTest {

    @Autowired
    ReviewService reviewService;

    @Autowired
    EntityManager em;

    @Autowired
    JPAQueryFactory queryFactory;

//    @Test
//    void test() {
//        List<ProductReviewDto> reviewsByProductId = reviewService.getReviewsByProductId(3L);
//        System.out.println("reviewsByProductId = " + reviewsByProductId);
//    }

    @Test
    void productReviewsByProductIdV2() {

        Long productId = 3L;

        List<ProductReviewDto> reviews = queryFactory.from(review)
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

        System.out.println("reviews = " + reviews);


    }

}