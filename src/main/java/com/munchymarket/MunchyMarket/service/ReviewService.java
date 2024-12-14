package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.domain.Image;
import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.domain.Product;
import com.munchymarket.MunchyMarket.domain.Review;
import com.munchymarket.MunchyMarket.dto.product.ProductReviewDto;
import com.munchymarket.MunchyMarket.dto.product.ReviewCreateDto;
import com.munchymarket.MunchyMarket.exception.DuplicateReviewException;
import com.munchymarket.MunchyMarket.exception.GcsFileUploadFailException;
import com.munchymarket.MunchyMarket.repository.image.ImageRepository;
import com.munchymarket.MunchyMarket.repository.member.MemberRepository;
import com.munchymarket.MunchyMarket.repository.product.ProductRepository;
import com.munchymarket.MunchyMarket.repository.review.ReviewRepository;
import com.munchymarket.MunchyMarket.service.common.CommonEntityService;
import com.munchymarket.MunchyMarket.utils.FileSizeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final GcsUploadService gcsUploadService;

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    private final CommonEntityService commonLogicsService;

    @Transactional
    public void createReview(ReviewCreateDto reviewCreateDto) throws DuplicateReviewException {


        Product product = commonLogicsService.findProductById(reviewCreateDto.getProductId());
        Member member = commonLogicsService.findMemberById(reviewCreateDto.getMemberId());

        // 중복 리뷰 등록 검증
        boolean reviewExists = reviewRepository.existsByMemberIdAndProductId(member.getId(), product.getId());
        if (reviewExists) {
            throw new DuplicateReviewException("既にこの商品に対するレビューが登録されています。"); // global exception handler
        }

        Review review = convertToEntity(member, product, reviewCreateDto);

        log.info("review: {}", review);

        List<MultipartFile> reviewImages = reviewCreateDto.getReviewImages();
        for (MultipartFile reviewImage : reviewImages) {
            String imageUrl = null;
            try {
                imageUrl = gcsUploadService.uploadToGcs(reviewImage); // serverFilename

                Image image = Image.builder()
                        .clientFilename(reviewImage.getOriginalFilename())
                        .serverFilename(imageUrl)
                        .fileSize(FileSizeUtils.readableFileSize(reviewImage.getSize()))
                        .build();

                imageRepository.save(image);

                review.addReviewImage(image); // cascade
            } catch (IOException e) {
                throw new GcsFileUploadFailException(e.getMessage());
            }

        }

        reviewRepository.save(review);
    }


    private Review convertToEntity(Member member, Product product, ReviewCreateDto reviewCreateDto) {
        return Review.builder()
                .member(member)
                .product(product)
                .rating(reviewCreateDto.getRating())
                .content(reviewCreateDto.getContent())
                .build();
    }

    @Value("${spring.cloud.gcp.storage.bucket}")
    private String bucketName;

    @Transactional
    public List<ProductReviewDto> getReviewsByProductId(Long productId) {

        List<ProductReviewDto> productReviews = reviewRepository.getProductReviewsByProductId(productId);
        // 각 DTO에 대해 GCP 스토리지 URL 설정
        productReviews.forEach(review -> review.setFinalReviewImages(bucketName));

        return productReviews;
    }
}
