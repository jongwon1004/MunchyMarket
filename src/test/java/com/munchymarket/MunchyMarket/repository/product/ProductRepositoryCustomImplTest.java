package com.munchymarket.MunchyMarket.repository.product;

import com.munchymarket.MunchyMarket.dto.RegisteredProductDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class ProductRepositoryCustomImplTest {

    private static final Logger log = LoggerFactory.getLogger(ProductRepositoryCustomImplTest.class);

    @Autowired
    ProductRepository productRepository;

    @Test
    void findRegisteredProduct() {
        // given
        Long productId = 1L;

        // when
        RegisteredProductDto registeredProductDto = productRepository.findByProductId(productId);

        log.info("registeredProductDto: {}", registeredProductDto);
        // then
        assertThat(registeredProductDto.categoryId()).isEqualTo(901003);
    }

}