package com.munchymarket.MunchyMarket.repository.cart.cart_products;

import com.munchymarket.MunchyMarket.dto.CartProductDto;
import com.munchymarket.MunchyMarket.repository.cart.CartRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartProductRepositoryCustomImplTest {


    @Autowired
    CartProductRepository cartProductRepository;

    @Test
    @DisplayName("회원 쇼핑카드 상품리스트 DTO 변환")
    public void 회원_쇼핑카트_상품리스트_DTO_변환() {

        List<CartProductDto> cartProductByMemberIdToDto = cartProductRepository.findCartProductByMemberIdToDto(2L);
        cartProductByMemberIdToDto.forEach(System.out::println);
    }

}