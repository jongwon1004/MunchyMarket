package com.munchymarket.MunchyMarket.controller.orderpay;

import com.munchymarket.MunchyMarket.dto.orderpay.OrderPaymentRequestDto;
import com.munchymarket.MunchyMarket.dto.wrapper.ApiResponse;
import com.munchymarket.MunchyMarket.security.CustomMemberDetails;
import com.munchymarket.MunchyMarket.service.OrderService;
import com.munchymarket.MunchyMarket.service.PaymentService;
import com.munchymarket.MunchyMarket.service.common.CommonEntityService;
import com.stripe.exception.StripeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    private final CommonEntityService commonEntityService;

    /**
     * 주문 생성 & PaymentIntent Create
     * TODO: 쿠폰 사용시 최소주문금액, 최대 할인 가능액 계산 다시 해야됨, 장바구니 구현 필요
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Map<String, Object>>> createOrder(@RequestBody OrderPaymentRequestDto orderPaymentRequestDto,
                                                                        @AuthenticationPrincipal CustomMemberDetails customMemberDetails)
            throws StripeException {

        Map<String, Object> response = new HashMap<>();


        Long memberId = customMemberDetails.getId();
        int orderTotal = orderService.createOrder(orderPaymentRequestDto, memberId); // amount

        Map<String, String> paymentIntent = paymentService.createPaymentIntent(orderPaymentRequestDto.getPayment(), memberId, orderTotal);

        response.put("paymentIntent", paymentIntent);
        response.put("message", "注文が確定されました。");


        // 요청 보내기전에 이미 클라이언트 쪽에서 해당 회원이 가지고 있는 쿠폰 리스트 데이터를 가지고 있음.
        // 쇼핑 카트에 화면에 회원이 가지고 있는 쿠폰 리스트 데이터를 리턴해줘야됨.

        // 쿠폰 사용시
        // -> 할인 상품은 할인된 가격으로 계산되어 총 금액에서 쿠폰 금액을 차감한 금액을 결제하게 됨.
        // -> 쿠폰은 한번에 하나만 사용 가능하며, 쿠폰은 한번 사용하면 재사용 불가능함.


        // 쿠폰 사용 안할시
        // -> 할인 상품은 할인된 가격으로 계산되어 총 금액을 결제하게 됨.

        return ResponseEntity.ok().body(ApiResponse.ofSuccess(response));
    }

    @PostMapping("/calculate")
    public ResponseEntity<?> calculate() {
        return ResponseEntity.ok().build();
    }
}
