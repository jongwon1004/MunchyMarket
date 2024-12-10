package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.dto.OrderPaymentRequestDto;
import com.munchymarket.MunchyMarket.service.OrderService;
import com.munchymarket.MunchyMarket.service.PaymentService;
import com.munchymarket.MunchyMarket.service.common.CommonLogicsService;
import com.munchymarket.MunchyMarket.utils.JWTUtil;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "결제 & 주문 API", description = "결제 & 주문 API 관리")
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    private final CommonLogicsService commonLogicsService;

    private final JWTUtil jwtUtil;

    private static final String ORDER_PAYMENT_CREATE_SUCCESS_RESPONSE_EXAM =
            "{\n" +
                    "  \"paymentIntent\": {\n" +
                    "    \"pi\": \"pi_3JONpfG19NWjX8Wx1OhNxhwd\",\n" +
                    "    \"clientSecret\": \"pi_3JONpfG19NWjX8Wx1OhNxhwd_secret_WdHells9on2zWkOrLD8kLotYU\"\n" +
                    "  },\n" +
                    "  \"message\": \"注文が確定されました。\"\n" +
                    "}";

    /**
     * 주문 생성 & PaymentIntent Create
     * TODO: 쿠폰 사용시 최소주문금액, 최대 할인 가능액 계산 다시 해야됨, 장바구니 구현 필요
     * @return
     */
    @Operation(
            summary = "주문 생성 & PaymentIntent Create",
            description = "주문 생성 & PaymentIntent Create",
            responses = {
                    @ApiResponse(responseCode = "200", description = "주문 & PaymentIntent 생성 성공"
                    ,content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    type = "object",
                                    example = ORDER_PAYMENT_CREATE_SUCCESS_RESPONSE_EXAM
                            )
                    )),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            }
    )
    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createOrder(@RequestBody OrderPaymentRequestDto orderPaymentRequestDto, @RequestHeader("Authorization") String tk)
            throws StripeException {

        Map<String, Object> response = new HashMap<>();


        Long memberId = jwtUtil.getId(tk);
        int orderTotal = orderService.createOrder(orderPaymentRequestDto, memberId); // amount

        Map<String, String> paymentIntent = paymentService.createPaymentIntent(orderPaymentRequestDto.getPayment(), memberId, orderTotal);

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
