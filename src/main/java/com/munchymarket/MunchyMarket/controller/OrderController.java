package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.domain.Product;
import com.munchymarket.MunchyMarket.dto.OrderPaymentRequestDto;
import com.munchymarket.MunchyMarket.dto.ProductIdAndQuantityDto;
import com.munchymarket.MunchyMarket.service.OrderService;
import com.munchymarket.MunchyMarket.service.PaymentService;
import com.munchymarket.MunchyMarket.service.common.CommonLogicsService;
import com.munchymarket.MunchyMarket.utils.JWTUtil;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    private final CommonLogicsService commonLogicsService;

    private final JWTUtil jwtUtil;

    /**
     * 주문 생성 & PaymentIntent Create
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody OrderPaymentRequestDto orderPaymentRequestDto, @RequestHeader("Authorization") String tk)
        throws StripeException {

        Map<String, Object> response = new HashMap<>();
        log.info("orderPaymentRequestDto: {}", orderPaymentRequestDto);
        if (!jwtUtil.getId(tk).equals(orderPaymentRequestDto.getMemberId())) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", "jwt tokenとmetadataのmemberIdが一致しません。"));
        }

        int orderTotal = orderService.createOrder(orderPaymentRequestDto); // amount

        Map<String, String> paymentIntent = paymentService.createPaymentIntent(orderPaymentRequestDto.getPayment(), orderPaymentRequestDto.getMemberId(), orderTotal);

        response.put("paymentIntent", paymentIntent);
        response.put("message", "注文が確定されました。");

        // Order 생성
        // cascade 를 사용하여 OrderProduct 생성

//        List<ProductIdAndQuantityDto> products = orderPaymentRequestDto.getOrder().getProducts();
//        log.info("products: {}", products);
//
//        List<Product> productEntityList = products.stream()
//                .map(product -> commonLogicsService.findProductById(product.getProductId()))
//                .toList();
//
//        int totalPrice = IntStream.range(0, products.size())
//                .map(i -> products.get(i).getQuantity() * productEntityList.get(i).getFinalPrice())
//                .sum();
//
//        log.info("totalPrice: {}", totalPrice);
//
//        log.info("productEntityList: {}", productEntityList);


        // 요청 보내기전에 이미 클라이언트 쪽에서 해당 회원이 가지고 있는 쿠폰 리스트 데이터를 가지고 있음.
        // 쇼핑 카트에 화면에 회원이 가지고 있는 쿠폰 리스트 데이터를 리턴해줘야됨.

        // 쿠폰 사용시
        // -> 할인 상품은 할인된 가격으로 계산되어 총 금액에서 쿠폰 금액을 차감한 금액을 결제하게 됨.
        // -> 쿠폰은 한번에 하나만 사용 가능하며, 쿠폰은 한번 사용하면 재사용 불가능함.


        // 쿠폰 사용 안할시
        // -> 할인 상품은 할인된 가격으로 계산되어 총 금액을 결제하게 됨.

        return ResponseEntity.ok().body(response);
    }
}
