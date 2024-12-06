package com.munchymarket.MunchyMarket.domain;

import com.munchymarket.MunchyMarket.domain.base.TimeBaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@ToString
@Table(name = "products")
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

    @Column(name = "final_price", nullable = false)
    private int finalPrice;

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

    @Column(name = "allergy_description", length = 500, nullable = false)
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

    @Lob
    @Column(name = "product_des_main", nullable = false, columnDefinition = "TEXT")
    private String productDesMain;

    @Column(name = "is_on_sale", columnDefinition = "tinyint(1) default 0")
    private Boolean isOnSale;

    @Column(name = "sale_percentage", columnDefinition = "int default 0")
    private int salePercentage;

    @Column(name = "is_purchase_status", columnDefinition = "tinyint(1) default 1")
    private Boolean isPurchaseStatus;

    @Builder
    public Product(Long id, Category category, String productName, int basePrice,int finalPrice, String shortDescription,
                   int stock, String deliveryDescription, PackagingType packagingType, String origin,
                   String unit, String volume, String expirationDescription, String allergyDescription,
                   String guideDescription, Image mainImage, Image subImage, String productDesTop1,
                   String productDesTop2, String productDesMain, Boolean isOnSale,
                   int salePercentage, Boolean isPurchaseStatus) {
        this.id = id;
        this.category = category;
        this.productName = productName;
        this.basePrice = basePrice;
        this.finalPrice = finalPrice;
        this.shortDescription = shortDescription;
        this.stock = stock;
        this.deliveryDescription = deliveryDescription;
        this.packagingType = packagingType;
        this.origin = origin;
        this.unit = unit;
        this.volume = volume;
        this.expirationDescription = expirationDescription;
        this.allergyDescription = allergyDescription;
        this.guideDescription = guideDescription;
        this.mainImage = mainImage;
        this.subImage = subImage;
        this.productDesTop1 = productDesTop1;
        this.productDesTop2 = productDesTop2;
        this.productDesMain = productDesMain;
        this.isOnSale = isOnSale;
        this.salePercentage = salePercentage;
        this.isPurchaseStatus = isPurchaseStatus;
    }
}
