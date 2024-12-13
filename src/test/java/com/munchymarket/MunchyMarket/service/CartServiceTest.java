package com.munchymarket.MunchyMarket.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CartServiceTest {


    @Autowired
    CartService cartService;

    private final Logger log = LoggerFactory.getLogger(CartServiceTest.class);


    @Test
    public void CartId_ProductId_값으로_CartProduct_삭제() {

        // given
        Long memberId = 2L;
        Long cartId = 2L;
        Long productId = 17L;

        int deletedRow = cartService.deleteProductFromCart(memberId, productId);

//        cartService.
//        Assertions.assertThat()
    }

}