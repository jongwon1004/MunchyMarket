package com.munchymarket.MunchyMarket.request;


import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@ToString
@Setter
public class ProductRequestDto {

    @NotNull(message = "カテゴリIDは必須です。")
    private Long categoryId;

    @NotBlank(message = "商品名は必須です。")
    @Size(min = 1, max = 50, message = "商品名は1文字以上100文字以下で入力してください。")
    private String productName;

    @PositiveOrZero(message = "基本価格は正の数でなければなりません。") // 0 포함 양수만 허용
    private int basePrice;

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
    @Size(max = 255, message = "アレルギー情報は255文字以下で入力してください")
    private String allergyDescription;

    @NotBlank(message = "保存方法を入力してください")
    @Size(max = 500, message = "案内事項は500文字以下で入力してください")
    private String guideDescription;

//    @NotBlank(message = "mainImageを登録してください")
//    @Size(max = 255, message = "mainImage名は255文字以下で入力してください")
    private MultipartFile mainImageClientFilename;

//    @NotBlank(message = "subImageを登録してください")
//    @Size(max = 255, message = "subImage名は255文字以下で入力してください")
    private MultipartFile subImageClientFilename;

    @NotBlank(message = "商品説明トップ1を入力してください")
    @Size(max = 255, message = "商品説明トップ1は255文字以下で入力してください")
    private String productDesTop1;

    @NotBlank(message = "商品説明トップ2を入力してください")
    @Size(max = 255, message = "商品説明トップ2は255文字以下で入力してください")
    private String productDesTop2;

    @NotBlank(message = "商品説明トップ4を入力してください")
    @Size(max = 500, message = "商品説明メインは500文字以下で入力してください")
    private String productDesTopMain;

    private Boolean isOnSale;

    @Positive(message = "セール率は正の数でなければなりません。")
    private BigDecimal salePercentage;

    private Boolean isPurchaseStatus;
}
