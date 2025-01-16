package com.munchymarket.MunchyMarket.controller.cart;

import com.munchymarket.MunchyMarket.dto.cart.CartProductDto;
import com.munchymarket.MunchyMarket.dto.cart.CartProductQuantityUpdateDto;
import com.munchymarket.MunchyMarket.dto.product.ProductIdAndQuantityDto;
import com.munchymarket.MunchyMarket.dto.wrapper.ApiResponse;
import com.munchymarket.MunchyMarket.security.CustomMemberDetails;
import com.munchymarket.MunchyMarket.service.CartService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    /**
     * 회원 카트의 상품 목록 조회
     */
    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<CartProductDto>>> getCartProducts(@AuthenticationPrincipal CustomMemberDetails customMemberDetails) {

        List<CartProductDto> cartProducts = cartService.getCartProducts(customMemberDetails.getId());
        return ResponseEntity.ok().body(ApiResponse.ofSuccess(cartProducts));
    }


    /**
     * 카트에 상품 추가
     * REQUEST EXAM: { "productId": 1, "quantity": 2 }
     */
    @PostMapping("/products")
    public ResponseEntity<ApiResponse<Map<String, Object>>> addProductToCart(@AuthenticationPrincipal CustomMemberDetails customMemberDetails,
                                                        @RequestBody ProductIdAndQuantityDto productIdAndQuantityDto) {

        Map<String, Object> response = cartService.addProductToCart(customMemberDetails.getId(), productIdAndQuantityDto);
        return ResponseEntity.ok(ApiResponse.ofSuccess(response));
    }


    /**
     * 카트에 상품수량 업데이트
     * REQUEST EXAM: /api/cart/products/{productId}?quantity=3
     */
    @PutMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<List<CartProductQuantityUpdateDto>>> updateProductQuantity(@PathVariable("productId") Long productId,
                                                                                                 @RequestParam(name = "quantity") int quantity,
                                                                                                 @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        List<CartProductQuantityUpdateDto> updatedData = cartService.updateProductQuantity(customMemberDetails.getId(), productId, quantity);

        return ResponseEntity.ok().body(ApiResponse.ofSuccess(updatedData));
    }


    /**
     * 카트에 상품삭제
     * REQUEST EXAM: /api/cart/products/{productId}
     */
    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<Map<String, Long>>> deleteProductFromCart(@PathVariable("productId") Long productId,
                                                   @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        Long deletedCartProductId = cartService.deleteProductFromCart(customMemberDetails.getId(), productId);
        return ResponseEntity.ok(ApiResponse.ofSuccess(Collections.singletonMap("deleted_cart_product_id", deletedCartProductId)));
    }

}
