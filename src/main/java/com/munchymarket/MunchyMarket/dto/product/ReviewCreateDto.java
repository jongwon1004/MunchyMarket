package com.munchymarket.MunchyMarket.dto.product;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class ReviewCreateDto {

    private Long memberId;
    private Long productId;
    private BigDecimal rating;
    private String content;

    private List<MultipartFile> reviewImages = new ArrayList<>();


}
