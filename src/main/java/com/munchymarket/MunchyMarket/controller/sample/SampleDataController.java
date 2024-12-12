package com.munchymarket.MunchyMarket.controller.sample;

import com.munchymarket.MunchyMarket.dto.RegisteredProductDto;
import com.munchymarket.MunchyMarket.dto.sample.SampleProductRequestDto;
import com.munchymarket.MunchyMarket.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 샘플 데이터 등록 컨트롤러
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/products/sample-data")
public class SampleDataController {

    private final ProductService productService;

    @PostMapping("/register")
    public ResponseEntity<String> registerProduct(@RequestBody List<SampleProductRequestDto> sampleProductRequestDto) {

        log.info("sampleProductRequestDto = {}", sampleProductRequestDto);

        for (SampleProductRequestDto productRequestDto : sampleProductRequestDto) {
            RegisteredProductDto registeredProductDto = productService.registerSampleProduct(productRequestDto);
            System.out.println("registeredProductDto = " + registeredProductDto);
        }

        return ResponseEntity.ok("이미지 경로 기반 등록 성공");
    }
}
