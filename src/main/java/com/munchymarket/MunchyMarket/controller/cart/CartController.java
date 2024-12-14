package com.munchymarket.MunchyMarket.controller.cart;

import com.munchymarket.MunchyMarket.domain.CartProduct;
import com.munchymarket.MunchyMarket.dto.cart.CartProductDto;
import com.munchymarket.MunchyMarket.dto.product.ProductIdAndQuantityDto;
import com.munchymarket.MunchyMarket.dto.wrapper.ApiResponse;
import com.munchymarket.MunchyMarket.dto.wrapper.MessageResponseWrapper;
import com.munchymarket.MunchyMarket.dto.wrapper.ResponseWrapper;
import com.munchymarket.MunchyMarket.security.CustomMemberDetails;
import com.munchymarket.MunchyMarket.service.CartService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/products")
    public ResponseEntity<ApiResponse<List<CartProductDto>>> getCartProducts(@AuthenticationPrincipal CustomMemberDetails customMemberDetails) {

        List<CartProductDto> cartProducts = cartService.getCartProducts(customMemberDetails.getId());

        return ResponseEntity.ok().body(ApiResponse.ofSuccess(cartProducts));
    }


    /**
     * 카트에 상품추가
     * REQUEST EXAM: { "productId": 1, "quantity": 2 }
     * TODO : 이미 카트에 해당 상품이 추가되어있으면, 수량 + 업데이트 해주고 프론트쪽에서는 이미 담은 상품의 수량을 추가했습니다 표시
     */
    @PostMapping("/products")
    public ResponseEntity<ApiResponse<Map<String, Long>>> addProductToCart(@AuthenticationPrincipal CustomMemberDetails customMemberDetails,
                                                        @RequestBody ProductIdAndQuantityDto productIdAndQuantityDto) {

        CartProduct cartProduct = cartService.addProductToCart(customMemberDetails.getId(), productIdAndQuantityDto);
        return ResponseEntity.ok(ApiResponse.ofSuccess(Collections.singletonMap("saved_cart_product_id", +cartProduct.getId())));
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Setter @Getter
    public static class CartProductSimpleDto {
        private Long productId;
        private int quantity;
        private LocalDateTime lastModifiedDate;
    }

    /**
     * 카트에 상품수량 업데이트
     * REQUEST EXAM: /api/cart/products/{productId}?quantity=3
     */
    @PutMapping("/products/{productId}")
    public ResponseEntity<ApiResponse<List<CartProductSimpleDto>>> updateProductQuantity(@PathVariable("productId") Long productId,
                                                                        @RequestParam(name = "quantity") int quantity,
                                                                        @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {
        List<CartProductSimpleDto> updatedData = cartService.updateProductQuantity(customMemberDetails.getId(), productId, quantity);

        return ResponseEntity.ok().body(ApiResponse.ofSuccess(updatedData));
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
