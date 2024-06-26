package com.munchymarket.MunchyMarket.controller.admin;

import com.munchymarket.MunchyMarket.dto.CategoryListDto;
import com.munchymarket.MunchyMarket.dto.RegisteredProductDto;
import com.munchymarket.MunchyMarket.request.ProductRequestDto;
import com.munchymarket.MunchyMarket.service.CategoryService;
import com.munchymarket.MunchyMarket.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/api/admin/product")
public class AdminProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @PostMapping("/register")
    public ResponseEntity<?> registerProduct(@Valid @ModelAttribute ProductRequestDto productRequestDto) {
        log.info("productRequest.getCategoryId() = {}", productRequestDto.getCategoryId());
        log.info("productRequest.getProductName() = {}", productRequestDto.getProductName());
        log.info("productRequest.getBasePrice() = {}", productRequestDto.getBasePrice());
        log.info("productRequest.getShortDescription() = {}", productRequestDto.getShortDescription());
        log.info("productRequest.getStock() = {}", productRequestDto.getStock());
        log.info("productRequest.getDeliveryDescription() = {}", productRequestDto.getDeliveryDescription());
        log.info("productRequest.getPackagingTypeId() = {}", productRequestDto.getPackagingTypeId());
        log.info("productRequest.getOrigin() = {}", productRequestDto.getOrigin());
        log.info("productRequest.getUnit() = {}", productRequestDto.getUnit());
        log.info("productRequest.getVolume() = {}", productRequestDto.getVolume());
        log.info("productRequest.getExpirationDescription() = {}", productRequestDto.getExpirationDescription());
        log.info("productRequest.getAllergyDescription() = {}", productRequestDto.getAllergyDescription());
        log.info("productRequest.getGuideDescription() = {}", productRequestDto.getGuideDescription());
        // Postman 테스트시 multipart/form 으로 key 값 images[mainImage], images[subImage] 이런식으로 보내야됨
        log.info("productRequest.get(\"mainImage\").getOriginalFilename() = {}", productRequestDto.getImages().get("mainImage").getOriginalFilename());
        log.info("productRequest.get(\"subImage\").getOriginalFilename() = {}", productRequestDto.getImages().get("subImage").getOriginalFilename());
        log.info("productRequest.getProductDesTop1() = {}", productRequestDto.getProductDesTop1());
        log.info("productRequest.getProductDesTop2() = {}", productRequestDto.getProductDesTop2());
        log.info("productRequest.getProductDesTopMain() = {}", productRequestDto.getProductDesTopMain());
        log.info("productRequest.getIsOnSale() = {}", productRequestDto.getIsOnSale());
        log.info("productRequest.getSalePercentage() = {}", productRequestDto.getSalePercentage());
        log.info("productRequest.getIsPurchaseStatus() = {}", productRequestDto.getIsPurchaseStatus());

        RegisteredProductDto registeredProductDto = productService.registerProduct(productRequestDto);
        return registeredProductDto != null ? ResponseEntity.ok().body(registeredProductDto) : ResponseEntity.badRequest().body("商品登録に失敗しました。");
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryListDto>> getCategories() {
        return ResponseEntity.ok().body(categoryService.getCategories());
    }


}
