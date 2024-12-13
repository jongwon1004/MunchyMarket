package com.munchymarket.MunchyMarket.repository.cart;

import com.munchymarket.MunchyMarket.domain.Product;

public interface CartRepositoryCustom {

    public void addProductToCart(Product product, int quantity);
}
