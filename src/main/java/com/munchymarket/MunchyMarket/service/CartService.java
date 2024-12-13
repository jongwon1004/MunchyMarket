package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.controller.CartController;
import com.munchymarket.MunchyMarket.domain.Cart;
import com.munchymarket.MunchyMarket.domain.CartProduct;
import com.munchymarket.MunchyMarket.domain.Product;
import com.munchymarket.MunchyMarket.dto.CartProductDto;
import com.munchymarket.MunchyMarket.dto.ProductIdAndQuantityDto;
import com.munchymarket.MunchyMarket.repository.cart.cart_products.CartProductRepository;
import com.munchymarket.MunchyMarket.service.common.CommonLogicsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {


    private final CommonLogicsService commonLogicsService;
    private final CartProductRepository cartProductRepository;

    /**
     * 회원의 쇼핑카트의 상품목록
     */
    public List<CartProductDto> getCartProducts(Long memberId) {
        return cartProductRepository.findCartProductByMemberIdToDto(memberId);
    }

    @Transactional(rollbackOn = Exception.class)
    public CartProduct addProductToCart(Long memberId, ProductIdAndQuantityDto productIdAndQuantityDto) {
        Product product = commonLogicsService.findProductById(productIdAndQuantityDto.getProductId());
        Cart cart = commonLogicsService.findCartByMemberId(memberId);

        CartProduct cartProduct = CartProduct.builder()
                .cart(cart)
                .product(product)
                .quantity(productIdAndQuantityDto.getQuantity())
                .build();

        return cartProductRepository.save(cartProduct);
    }

    @Transactional(rollbackOn = Exception.class)
    public List<CartController.CartProductSimpleDto> updateProductQuantity(Long memberId, Long productId, int quantity) {

        Cart cart = commonLogicsService.findCartByMemberId(memberId);

        CartProduct cartProduct = cartProductRepository.findCartProductByCartIdAndProductId(cart.getId(), productId);
        if (cartProduct == null) {
            throw new IllegalArgumentException("Product not found in cart.");
        }

        cartProduct.changeQuantity(quantity);

        return cartProductRepository.findCartProductsByCartId(cart.getId())
                .stream()
                .map(i -> new CartController.CartProductSimpleDto(i.getProduct().getId(), i.getQuantity(), i.getLastModifiedDate()))
                .toList();
    }



    public int deleteProductFromCart(Long memberId, Long productId) {
        Cart cart = commonLogicsService.findCartByMemberId(memberId);
        int deletedRow = cartProductRepository.deleteCartProductByCartIdAndProductId(cart.getId(), productId);
        return deletedRow;
    }


}