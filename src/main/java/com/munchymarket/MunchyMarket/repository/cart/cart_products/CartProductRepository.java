package com.munchymarket.MunchyMarket.repository.cart.cart_products;

import com.munchymarket.MunchyMarket.domain.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct, Long>, CartProductRepositoryCustom {

    CartProduct findCartProductByCartIdAndProductId(Long cartId, Long productId);

    List<CartProduct> findCartProductsByCartId(Long cartId);

    int deleteCartProductByCartIdAndProductId(Long cartId, Long product2Id);
}
