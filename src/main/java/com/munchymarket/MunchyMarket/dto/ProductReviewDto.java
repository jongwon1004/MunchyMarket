package com.munchymarket.MunchyMarket.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
@ToString
public class ProductReviewDto {

    private Long reviewId;
    private Long memberId;
    private String memberName;
    private BigDecimal rating;
    private String content;

    @JsonIgnore
    private String reviewImagesRaw; // "image1.jpg, image2.jpg, image3.jpg"
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    private List<String> reviewImages = new ArrayList<>();


    public ProductReviewDto(Long reviewId, Long memberId, String memberName, BigDecimal rating, String content, String reviewImagesRaw, LocalDateTime createdDate, LocalDateTime lastModifiedDate, List<String> reviewImages) {
        this.reviewId = reviewId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.rating = rating;
        this.content = content;
        this.reviewImagesRaw = reviewImagesRaw;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.reviewImages = reviewImages;
    }

    // GCP 스토리지 URL 생성 메서드
    public void setFinalReviewImages(String bucketName) {
        if (reviewImagesRaw != null && !reviewImagesRaw.isEmpty()) {
            this.reviewImages = Arrays.stream(reviewImagesRaw.split(","))
                    .map(image -> "https://storage.googleapis.com/" + bucketName + "/" + image.trim())
                    .toList();
        } else {
            this.reviewImages.clear();
        }
    }


}
