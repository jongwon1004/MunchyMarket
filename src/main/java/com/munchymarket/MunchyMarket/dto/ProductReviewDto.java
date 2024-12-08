package com.munchymarket.MunchyMarket.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    private List<String> reviewImages;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;


    public ProductReviewDto(Long reviewId, Long memberId, String memberName, BigDecimal rating, String content, List<String> reviewImages, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.reviewId = reviewId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.rating = rating;
        this.content = content;
        this.reviewImages = reviewImages;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
