package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.domain.CartProduct;
import com.munchymarket.MunchyMarket.dto.CartProductDto;
import com.munchymarket.MunchyMarket.dto.ProductIdAndQuantityDto;
import com.munchymarket.MunchyMarket.security.CustomMemberDetails;
import com.munchymarket.MunchyMarket.service.CartService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter @Setter
    static class ResponseFormat<T> {
        private String message;
        private T data;
    }


    @GetMapping("/products")
    public ResponseEntity<?> getCartProducts(@AuthenticationPrincipal CustomMemberDetails customMemberDetails) {

        List<CartProductDto> cartProducts = cartService.getCartProducts(customMemberDetails.getId());

        return ResponseEntity.ok().body(new ResponseFormat<>("success", cartProducts));
    }


    /**
     * 카트에 상품추가
     * REQUEST EXAM: { "productId": 1, "quantity": 2 }
     */
    @PostMapping("/products")
    public ResponseEntity<?> addProductToCart(@AuthenticationPrincipal CustomMemberDetails customMemberDetails,
                                              @RequestBody ProductIdAndQuantityDto productIdAndQuantityDto) {

        CartProduct cartProduct = cartService.addProductToCart(customMemberDetails.getId(), productIdAndQuantityDto);
        return ResponseEntity.ok(new ResponseFormat<>("success", "saved cp_id = "+cartProduct.getId()));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter @Getter
    public static class CartProductSimpleDto {
        private Long productId;
        private int quantity;
        private LocalDateTime lastModifiedDate;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Setter @Getter
    public static class UpdateProductQuantityResponse {
        private String message;
        private List<CartProductSimpleDto> data;
    }


    /**
     * 카트에 상품수량 업데이트
     * REQUEST EXAM: /api/cart/products/{productId}?quantity=3
     */
    @PutMapping("/products/{productId}")
    public ResponseEntity<UpdateProductQuantityResponse> updateProductQuantity(@PathVariable("productId") Long productId,
                                                   @RequestParam(name = "quantity", required = true) int quantity,
                                                   @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        List<CartProductSimpleDto> updatedData = cartService.updateProductQuantity(customMemberDetails.getId(), productId, quantity);

        UpdateProductQuantityResponse response = new UpdateProductQuantityResponse("Product quantity updated.", updatedData);
        return ResponseEntity.ok().body(response);
    }


    /**
     * 카트에 상품삭제
     * REQUEST EXAM: /api/cart/products/{productId}
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProductFromCart(@PathVariable("productId") Long productId,
                                                   @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        cartService.deleteProductFromCart(customMemberDetails.getId(), productId);
        return ResponseEntity.ok().build();
    }

}
