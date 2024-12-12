package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.dto.AsyncTestDto;
import com.munchymarket.MunchyMarket.dto.ProductIdAndQuantityDto;
import com.munchymarket.MunchyMarket.service.common.CommonLogicsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class AsyncService {

    private final CommonLogicsService commonLogicsService;

    @Async
    public CompletableFuture<Map<String, Integer>> run(AsyncTestDto asyncTestDto) throws InterruptedException {
        return CompletableFuture.completedFuture(hello(asyncTestDto));
    }

    public Map<String, Integer> hello(AsyncTestDto asyncTestDto) throws InterruptedException {
        List<ProductIdAndQuantityDto> products = asyncTestDto.getOrder().getProducts();

        int resultPrice = 0;

        resultPrice = products.stream()
                .map(i -> commonLogicsService.findProductById(i.getProductId()).getFinalPrice() * i.getQuantity())
                .mapToInt(Integer::intValue)
                .sum();

        return Collections.singletonMap("resultPrice", resultPrice);


    }
}
