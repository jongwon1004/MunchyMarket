package com.munchymarket.MunchyMarket.controller;


import com.munchymarket.MunchyMarket.request.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {


    @PostMapping("/register")
    public ResponseEntity<?> registerProduct(@ModelAttribute ProductRequestDto productRequestDto) {
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
        log.info("productRequest.getMainImage() = {}", productRequestDto.getMainImageClientFilename().getOriginalFilename());
        log.info("productRequest.getSubImage() = {}", productRequestDto.getSubImageClientFilename().getOriginalFilename());
        log.info("productRequest.getProductDesTop1() = {}", productRequestDto.getProductDesTop1());
        log.info("productRequest.getProductDesTop2() = {}", productRequestDto.getProductDesTop2());
        log.info("productRequest.getProductDesTopMain() = {}", productRequestDto.getProductDesTopMain());
        log.info("productRequest.getIsOnSale() = {}", productRequestDto.getIsOnSale());
        log.info("productRequest.getSalePercentage() = {}", productRequestDto.getSalePercentage());
        log.info("productRequest.getIsPurchaseStatus() = {}", productRequestDto.getIsPurchaseStatus());

        return ResponseEntity.ok().build();

    }
}
