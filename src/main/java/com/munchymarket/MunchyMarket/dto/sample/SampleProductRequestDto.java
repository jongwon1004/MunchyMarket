package com.munchymarket.MunchyMarket.dto.sample;


import com.munchymarket.MunchyMarket.domain.Category;
import com.munchymarket.MunchyMarket.domain.Image;
import com.munchymarket.MunchyMarket.domain.PackagingType;
import com.munchymarket.MunchyMarket.domain.Product;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@Getter
@ToString
@Setter
public class SampleProductRequestDto {

    @NotNull(message = "カテゴリIDは必須です。")
    private Long categoryId;

    @NotBlank(message = "商品名は必須です。")
    @Size(min = 1, max = 50, message = "商品名は1文字以上100文字以下で入力してください。")
    private String productName;

    @PositiveOrZero(message = "基本価格は正の数でなければなりません。") // 0 포함 양수만 허용
    private int basePrice;

    @PositiveOrZero(message = "最終価格は正の数でなければなりません。")
    private int finalPrice;

    @Size(max = 500, message = "短い説明は255文字以下で入力してください。")
    private String shortDescription;

    @PositiveOrZero(message = "在庫は正の数でなければなりません。") // 0 포함 양수만 허용
    private int stock;

    @Size(max = 255, message = "配送説明は255文字以下で入力してください。")
    private String deliveryDescription;

    @NotNull(message = "包装タイプIDを入力してください")
    private Long packagingTypeId;

    @NotBlank(message = "原産地を入力してください")
    @Size(max = 50, message = "原産地は50文字以下で入力してください")
    private String origin;

    @NotBlank(message = "販売単位を入力してください")
    @Size(max = 50, message = "販売単位は50文字以下で入力してください")
    private String unit;

    @NotBlank(message = "容量・重量を入力してください")
    @Size(max = 50, message = "容量・重量は50文字以下で入力してください")
    private String volume;

    @NotBlank(message = "賞味期限の説明を入力してください")
    @Size(max = 255, message = "賞味期限の説明は255文字以下で入力してください")
    private String expirationDescription;

    @NotBlank(message = "アレルギー情報を入力してください")
    @Size(max = 500, message = "アレルギー情報は255文字以下で入力してください")
    private String allergyDescription;

    @NotBlank(message = "保存方法を入力してください")
    @Size(max = 500, message = "案内事項は500文字以下で入力してください")
    private String guideDescription;

    // uploadToGcs 메서드에서 파라미터를 list로 받기위해 front에서 Map 으로 받은 후 list로 변환
    private Map<String, String> productImages = new HashMap<>();


    @NotBlank(message = "商品説明トップ1を入力してください")
    @Size(max = 255, message = "商品説明トップ1は255文字以下で入力してください")
    private String productDesTop1;

    @NotBlank(message = "商品説明トップ2を入力してください")
    @Size(max = 255, message = "商品説明トップ2は255文字以下で入力してください")
    private String productDesTop2;

    @NotBlank(message = "商品説明トップ4を入力してください")
    @Size(message = "商品説明メインは500文字以下で入力してください")
    private String productDesTopMain;

    private Boolean isOnSale;

    private int salePercentage;

    private Boolean isPurchaseStatus;

    public Product toEntity(Category category, PackagingType packagingType, Image mainImage, Image subImage) {
        return Product.builder()
                .category(category)
                .productName(this.productName)
                .basePrice(this.basePrice)
                .finalPrice(this.finalPrice)
                .shortDescription(this.shortDescription)
                .stock(this.stock)
                .deliveryDescription(this.deliveryDescription)
                .packagingType(packagingType)
                .origin(this.origin)
                .unit(this.unit)
                .volume(this.volume)
                .expirationDescription(this.expirationDescription)
                .allergyDescription(this.allergyDescription)
                .guideDescription(this.guideDescription)
                .mainImage(mainImage)
                .subImage(subImage)
                .productDesTop1(this.productDesTop1)
                .productDesTop2(this.productDesTop2)
                .productDesMain(this.productDesTopMain)
                .isOnSale(this.isOnSale)
                .salePercentage(this.salePercentage)
                .isPurchaseStatus(this.isPurchaseStatus)
                .build();
    }
}
