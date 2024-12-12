package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;


    @PostMapping("/products")
    public ResponseEntity<?> addProductToCart() {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<?> updateProductQuantity() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProductFromCart() {
        return ResponseEntity.ok().build();
    }

}
