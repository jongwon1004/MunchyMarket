package com.munchymarket.MunchyMarket.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
public class Category {

    @Id
    @Column(name = "category_id", updatable = false)
    private Long id;

    /**
     * 각 카테고리는 하나의 부모 카테고리를 가질 수 있고, 다수의 자식 카테고리가 동일한 부모 카테고리를 가질 수 있다.
     * 여러 자식 카테고리(Many)는 하나의 부모 카테고리(One)를 가질 수 있다.
     * 부모 카테고리는 자식 카테고리를 여러 개 가질 수 있다.
     * 부모 카테고리와 자식 카테고리는 다대일 관계이다.
     * 지금 이 카테고리 객체는 자식 카테고리이다.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    @Column(name = "category_name", length = 50, nullable = false)
    private String categoryName;
}
