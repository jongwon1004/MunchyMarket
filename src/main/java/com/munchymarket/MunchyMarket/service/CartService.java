package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.controller.cart.CartController;
import com.munchymarket.MunchyMarket.domain.Cart;
import com.munchymarket.MunchyMarket.domain.CartProduct;
import com.munchymarket.MunchyMarket.domain.Product;
import com.munchymarket.MunchyMarket.dto.cart.CartProductDto;
import com.munchymarket.MunchyMarket.dto.product.ProductIdAndQuantityDto;
import com.munchymarket.MunchyMarket.repository.cart.cart_products.CartProductRepository;
import com.munchymarket.MunchyMarket.service.common.CommonEntityService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartService {


    private final CommonEntityService commonEntityService;
    private final CartProductRepository cartProductRepository;

    /**
     * 회원의 쇼핑카트의 상품목록
     */
    @Transactional(readOnly = true)
    public List<CartProductDto> getCartProducts(Long memberId) {
        return cartProductRepository.findCartProductByMemberIdToDto(memberId);
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> addProductToCart(Long memberId, ProductIdAndQuantityDto productIdAndQuantityDto) {
        Map<String, Object> response = new HashMap<>();

        CommonResult commonResult = commonLogics(memberId, productIdAndQuantityDto.getProductId(), MethodFrom.ADD_PRODUCT);
        if(MessageConstants.PRODUCT_ALREADY_IN_CART.equals(commonResult.message())) {
            response.put("message", MessageConstants.PRODUCT_ALREADY_IN_CART);
            CartProduct cartProduct = commonResult.cartProduct();
            cartProduct.changeQuantity(cartProduct.getQuantity() + productIdAndQuantityDto.getQuantity());
            response.put("changed_cart_product_id", cartProduct.getId());
            return response;
        }

        CartProduct cartProduct = CartProduct.builder()
                .cart(commonResult.cart())
                .product(commonResult.product())
                .quantity(productIdAndQuantityDto.getQuantity())
                .build();

        cartProductRepository.save(cartProduct);

        response.put("saved_cart_product_id", cartProduct.getId());
        return response;
    }


    public record CommonResult(Cart cart, Product product, CartProduct cartProduct, String message) {}

    public static class MessageConstants {
        public static final String PRODUCT_ADDED_TO_CART = "商品をカートに追加しました";
        public static final String PRODUCT_QUANTITY_UPDATED = "商品の数量を更新しました";
        public static final String PRODUCT_ALREADY_IN_CART = "既にカートに商品が追加されているため、数量を追加しました";
    }

    public enum MethodFrom{
        ADD_PRODUCT, UPDATE_PRODUCT
    }


    // 카트에 추가하려는데 해당 상품이 이미 카트에 존재하는 경우 수량을 업데이트 해줘야함
    // 카트에서 수량 변경하는 메서드는 이미 존재하기 때문에 공통으로 묶어주고 싶음.
    public CommonResult commonLogics(Long memberId, Long productId, MethodFrom from) {
        Product product = commonEntityService.findProductById(productId);
        Cart cart = commonEntityService.findCartByMemberId(memberId);
        CartProduct cartProduct = cartProductRepository.findCartProductByCartIdAndProductId(cart.getId(), productId);

        if (cartProduct == null) {
            if (from == MethodFrom.UPDATE_PRODUCT) {
                throw new IllegalArgumentException("Product not found in cart for update.");
            }
            return new CommonResult(cart, product, null, MessageConstants.PRODUCT_ADDED_TO_CART);
        } else {
            if(from == MethodFrom.ADD_PRODUCT) {
                return new CommonResult(cart, product, cartProduct, MessageConstants.PRODUCT_ALREADY_IN_CART);
            }
        }
        // cartProduct != null && from == MethodFrom.UPDATEPRODUCT
        return new CommonResult(cart, product, cartProduct, MessageConstants.PRODUCT_QUANTITY_UPDATED);
    }



    @Transactional(rollbackFor = Exception.class)
    public List<CartController.CartProductSimpleDto> updateProductQuantity(Long memberId, Long productId, int quantity) {

        CommonResult commonResult = commonLogics(memberId, productId, MethodFrom.UPDATE_PRODUCT);

        CartProduct cartProduct = commonResult.cartProduct();
        cartProduct.changeQuantity(quantity);

        return cartProductRepository.findCartProductsByCartId(commonResult.cart().getId())
                .stream()
                .map(i -> new CartController.CartProductSimpleDto(i.getProduct().getId(), i.getQuantity(), i.getLastModifiedDate()))
                .toList();
    }


    @Transactional(rollbackFor = Exception.class)
    public Long deleteProductFromCart(Long memberId, Long productId) {
        Cart cart = commonEntityService.findCartByMemberId(memberId);
        CartProduct cartProduct = cartProductRepository.findCartProductByCartIdAndProductId(cart.getId(), productId);
        if (cartProduct == null) {
            throw new EntityNotFoundException("cartProduct not found");
        }
        return cartProductRepository.deleteCartProductByCartIdAndProductId(cart.getId(), productId);
    }


}
