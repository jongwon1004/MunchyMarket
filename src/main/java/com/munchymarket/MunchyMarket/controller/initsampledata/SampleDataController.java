package com.munchymarket.MunchyMarket.controller.initsampledata;

import com.munchymarket.MunchyMarket.dto.product.RegisteredProductDto;
import com.munchymarket.MunchyMarket.dto.initsampledata.SampleProductRequestDto;
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

    /**
     * 클라이언트 화면에서 상품등록이 아닌, POSTMAN 으로 샘플데이터 등록 요청
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerProduct(@RequestBody List<SampleProductRequestDto> sampleProductRequestDto) {

        log.info("sampleProductRequestDto = {}", sampleProductRequestDto);

        for (SampleProductRequestDto productRequestDto : sampleProductRequestDto) {
            RegisteredProductDto registeredProductDto = productService.registerSampleProduct(productRequestDto);
            System.out.println("registeredProductDto = " + registeredProductDto);
        }

        return ResponseEntity.ok("샘플 데이터 등록완료");
    }
}
