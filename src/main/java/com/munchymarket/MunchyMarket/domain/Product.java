package com.munchymarket.MunchyMarket.domain;

import com.munchymarket.MunchyMarket.domain.base.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@Entity
public class Product extends TimeBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "product_name", length = 50, nullable = false)
    private String productName;

    @Column(name = "base_price", nullable = false)
    private int basePrice;

    @Column(name = "short_description", length = 500, nullable = false)
    private String shortDescription;

    @Column(name = "stock", nullable = false, columnDefinition = "int default 50")
    private int stock;

    @Column(name = "delivery_description", nullable = false)
    private String deliveryDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "packaging_type_id", nullable = false)
    private PackagingType packagingType;

    @Column(name = "origin", length = 50, nullable = false)
    private String origin;

    @Column(name = "unit", length = 30, nullable = false)
    private String unit;

    @Column(name = "volume", length = 30, nullable = false)
    private String volume;

    @Column(name = "expiration_description", nullable = false)
    private String expirationDescription;

    @Column(name = "allergy_description", nullable = false)
    private String allergyDescription;

    @Column(name = "guide_description", length = 500, nullable = false)
    private String guideDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "main_image_id", nullable = false)
    private Image mainImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_image_id", nullable = false)
    private Image subImage;

    @Column(name = "product_des_top1")
    private String productDesTop1;

    @Column(name = "product_des_top2")
    private String productDesTop2;

    @Column(name = "product_des_top3")
    private String productDesTop3;

    @Lob
    @Column(name = "product_des_main", nullable = false, columnDefinition = "TEXT")
    private String productDesMain;

    @Column(name = "is_on_sale", nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean isOnSale;

    @Column(name = "sale_percentage", columnDefinition = "decimal(10,2) default 0")
    private double salePercentage;

    @Column(name = "is_purchase_status", nullable = false, columnDefinition = "tinyint(1) default 1")
    private Boolean isPurchaseStatus;

}
