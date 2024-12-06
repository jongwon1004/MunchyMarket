package com.munchymarket.MunchyMarket.domain;

import com.google.type.Decimal;
import com.munchymarket.MunchyMarket.domain.base.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Builder
@Table(
        name = "reviews",
        uniqueConstraints = @UniqueConstraint(columnNames = {"member_id", "product_id"})
)
@Entity
public class Review extends TimeBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private BigDecimal rating;

    @Column(nullable = false, length = 500)
    private String content;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    // @Builder 를 사용해서 객체를 생성하면 초기화된 필드값(new ArrayList<>()) 가 무시될때가 있음
    // Lombok 의 빌더가 모든 필드를 명시적으로 초기화 하지 않기 떄문 -> 초기화된 필드값도 null 로 덮어씌울 가능성이 있음
    // 따라서 @Builder.Default 를 사용해서 초기화된 필드값을 덮어씌우지 않도록 해야함
    private List<ReviewImage> reviewImages = new ArrayList<>();

    // 연관관계 메서드
    public void addReviewImage(Image image) {
        ReviewImage reviewImage = new ReviewImage(this, image);
        reviewImages.add(reviewImage);
    }


}

