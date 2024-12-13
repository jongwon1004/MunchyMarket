package com.munchymarket.MunchyMarket.repository.cart.cart_products;

import com.munchymarket.MunchyMarket.dto.CartProductDto;

import java.util.List;

public interface CartProductRepositoryCustom {

    List<CartProductDto> findCartProductByMemberIdToDto(Long memberId);
}
