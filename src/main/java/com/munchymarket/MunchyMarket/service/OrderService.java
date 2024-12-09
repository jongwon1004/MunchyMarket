package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.domain.Coupon;
import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.domain.Order;
import com.munchymarket.MunchyMarket.domain.Product;
import com.munchymarket.MunchyMarket.domain.enums.CouponType;
import com.munchymarket.MunchyMarket.dto.OrderDto;
import com.munchymarket.MunchyMarket.dto.OrderPaymentRequestDto;
import com.munchymarket.MunchyMarket.dto.ProductIdAndQuantityDto;
import com.munchymarket.MunchyMarket.repository.coupon.CouponRepository;
import com.munchymarket.MunchyMarket.repository.order.OrderRepository;
import com.munchymarket.MunchyMarket.repository.product.ProductRepository;
import com.munchymarket.MunchyMarket.service.common.CommonLogicsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CommonLogicsService commonLogicsService;
    private final ProductRepository productRepository;

    @Transactional
    public void createOrder(OrderPaymentRequestDto orderPaymentRequestDto) {

        // 회원찾는 쿼리 1 방 ( 계: 1회 )
        Member member = commonLogicsService.findMemberById(orderPaymentRequestDto.getMemberId());

        List<ProductIdAndQuantityDto> products = orderPaymentRequestDto.getOrder().getProducts();

        // DB connection 을 최소화 하기 위해 한번에 productIds를 가져옴
        List<Long> productIds = products.stream()
                .map(ProductIdAndQuantityDto::getProductId)
                .collect(Collectors.toList());

        // productIds 을 통해 productMap 을 생성 ( 쿼리 1방밖에 안나감 ) ( 계: 2회 )
        Map<Long, Product> productMap = productRepository.findAllById(productIds)
                .stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        // productMap 을 통해 productEntityList 를 생성
        List<Product> productEntityList = products.stream()
                .map(product -> productMap.get(product.getProductId()))
                .toList();

        // 할인 전 총 금액
        int subTotal = IntStream.range(0, products.size())
                .map(i -> products.get(i).getQuantity() * productEntityList.get(i).getBasePrice())
                .sum();

        log.info("subTotal: {}", subTotal);


        // 할인 후 금액
        int total = IntStream.range(0, products.size())
                .map(i -> products.get(i).getQuantity() * productEntityList.get(i).getFinalPrice())
                .sum();

        log.info("total: {}", total);

        // 쿠폰을 적용하지 않는 경우
        if(orderPaymentRequestDto.getCouponId() == null) {
            Order order = Order.builder()
                    .memberId(member)
                    .discountAmount(subTotal- total)
                    .subtotal(subTotal)
                    .total(total)
                    .build();

            orderRepository.save(order); // Order 생성
            for (int i = 0; i < orderPaymentRequestDto.getOrder().getProducts().size(); i++) {
                Product product = productEntityList.get(i);
                int quantity = orderPaymentRequestDto.getOrder().getProducts().get(i).getQuantity();

                order.addOrderProduct(product, quantity);
            }
            return;
        }

        // 쿠폰을 적용하는 경우 ( 계 : 3회)
        Coupon coupon = commonLogicsService.findCouponById(orderPaymentRequestDto.getCouponId());

        int couponDiscountedAmount = (coupon.getCouponType() == CouponType.FIXED)
                ? coupon.getDiscountValue() // coupon 타입이 FIXED 일 경우
                : total * coupon.getDiscountValue() / 100; // coupon 타입이 PERCENTAGE 일 경우

        total -= couponDiscountedAmount;

        Order order = Order.builder()
                .memberId(member)
                .discountAmount(subTotal - total)
                .subtotal(subTotal)
                .total(total)
                .couponId(coupon)
                .couponDiscountedAmount(couponDiscountedAmount)
                .build();

        orderRepository.save(order); // Order 생성 ( 계 : 4회 ), 쿠폰을 적용하지 않는경우 ( 계 : 3회 )

        for (int i = 0; i < orderPaymentRequestDto.getOrder().getProducts().size(); i++) {
            Product product = productEntityList.get(i);
            int quantity = orderPaymentRequestDto.getOrder().getProducts().get(i).getQuantity();

            order.addOrderProduct(product, quantity);
        }
    }
}
