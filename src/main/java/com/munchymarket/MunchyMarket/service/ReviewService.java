package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.domain.Product;
import com.munchymarket.MunchyMarket.domain.Review;
import com.munchymarket.MunchyMarket.dto.RegisteredProductDto;
import com.munchymarket.MunchyMarket.dto.ReviewCreateDto;
import com.munchymarket.MunchyMarket.repository.member.MemberRepository;
import com.munchymarket.MunchyMarket.repository.product.ProductRepository;
import com.munchymarket.MunchyMarket.repository.review.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final GcsUploadService gcsUploadService;

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void createReview(ReviewCreateDto reviewCreateDto) {

        Product product = findProductById(reviewCreateDto.getProductId());
        Member member = findMemberById(reviewCreateDto.getMemberId());

        Review review = convertToEntity(member, product, reviewCreateDto);
        Review savedReview = reviewRepository.save(review);

        log.info("Saved review ID: {}", savedReview.getId());
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    private Review convertToEntity(Member member, Product product, ReviewCreateDto reviewCreateDto) {
        return Review.builder()
                .member(member)
                .product(product)
                .rating(reviewCreateDto.getRating())
                .content(reviewCreateDto.getContent())
                .build();
    }




}
