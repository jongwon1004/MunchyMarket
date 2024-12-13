package com.munchymarket.MunchyMarket.repository.cart;

import com.munchymarket.MunchyMarket.domain.Cart;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class CartRepositoryTest {

    @Autowired
    CartRepository cartRepository;


    @Test
    @Transactional
    void 회원PK로_카트엔티티_조회() {
        // given
        Long memberId = 1L;

        // when
        Cart cart = cartRepository.findCartByMemberId(memberId).orElseThrow(() -> new IllegalArgumentException("Cart not found"));

        // then
        assertThat(cart.getMember().getId()).isEqualTo(memberId);
    }

}