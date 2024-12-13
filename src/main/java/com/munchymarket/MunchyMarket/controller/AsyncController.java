package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.domain.Product;
import com.munchymarket.MunchyMarket.dto.AsyncTestDto;
import com.munchymarket.MunchyMarket.dto.ProductIdAndQuantityDto;
import com.munchymarket.MunchyMarket.repository.cart.cart_products.CartProductRepository;
import com.munchymarket.MunchyMarket.service.common.CommonLogicsService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/async")
public class AsyncController {


    private final CartProductRepository cartProductRepository;
    private final CommonLogicsService commonLogicsService;



    @PostMapping("/run")
    public ResponseEntity<Map<String, Object>> hello(@RequestBody AsyncTestDto asyncTestDto) {

        Map<String, Object> response = new HashMap<>();


        List<ProductIdAndQuantityDto> products = asyncTestDto.getOrder().getProducts();
        List<Product> productList = products.stream()
                .map(product -> commonLogicsService.findProductById(product.getProductId()))
                .toList();

        // 상품 리스트에서 가격 * 수량 합계를 계산
        double totalPrice = products.stream()
                .mapToDouble(product -> {
                    Product foundProduct = commonLogicsService.findProductById(product.getProductId());
                    double productPrice = foundProduct.getBasePrice(); // 상품의 가격 가져오기
                    int quantity = product.getQuantity(); // products의 quantity 가져오기
                    return productPrice * quantity; // 가격 * 수량 계산
                })
                .sum();

        response.put("totalPrice", totalPrice);



        return ResponseEntity.ok().body(response);
    }
}
