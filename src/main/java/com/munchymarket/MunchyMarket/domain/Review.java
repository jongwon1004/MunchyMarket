package com.munchymarket.MunchyMarket.domain;

import com.google.type.Decimal;
import com.munchymarket.MunchyMarket.domain.base.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

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


}

