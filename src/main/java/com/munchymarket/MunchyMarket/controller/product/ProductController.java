package com.munchymarket.MunchyMarket.controller.product;


import com.munchymarket.MunchyMarket.dto.product.ProductDetailDto;
import com.munchymarket.MunchyMarket.dto.wrapper.ApiResponse;
import com.munchymarket.MunchyMarket.service.ProductService;
import com.munchymarket.MunchyMarket.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ReviewService reviewService;

    /**
     * 상품 상세정보
     * TODO : 상품후기, 상품후기 사진, 상품 문의 추가하니까 response DTO 새로 만들어야됨 (현재 상품 상세정보만 return)
     *
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductDetailDto>> getProduct(@PathVariable Long productId) {

        ProductDetailDto productDetailDto = new ProductDetailDto();
        productDetailDto.setProduct(productService.getProduct(productId));
        productDetailDto.setReviews(reviewService.getReviewsByProductId(productId));

        log.info("productDetailDto: {}", productDetailDto);


        return ResponseEntity.ok().body(ApiResponse.ofSuccess(productDetailDto));

    }

}
