package com.munchymarket.MunchyMarket.domain;

import com.munchymarket.MunchyMarket.domain.base.TimeBaseEntity;
import com.munchymarket.MunchyMarket.domain.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "orders")
public class Order extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Column(name = "order_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime orderDate = LocalDateTime.now();

    @Column(name = "status", nullable = false, length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'PENDING'")
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "sub_total", nullable = false)
    private Integer subtotal;

    @Column(name = "discount_amount", nullable = false, columnDefinition = "INT DEFAULT 0")
    private Integer discountAmount;

    @Column(name = "total", nullable = false)
    private Integer total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon couponId;

    @Column(name = "coupon_discounted_amount", columnDefinition = "INT DEFAULT 0")
    private Integer couponDiscountedAmount;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts = new ArrayList<>();


    @Builder
    public Order(Member memberId, Integer subtotal, Integer discountAmount, Integer total, Coupon couponId, Integer couponDiscountedAmount) {
        this.memberId = memberId;
        this.subtotal = subtotal;
        this.discountAmount = discountAmount;
        this.total = total;
        this.couponId = couponId;
        this.couponDiscountedAmount = couponDiscountedAmount;
    }

    // 연관관계 메서드
    public void addOrderProduct(Product product, int quantity) {
        OrderProduct orderProduct = OrderProduct.builder()
                .order(this)
                .product(product)
                .quantity(quantity)
                .basePrice(product.getBasePrice())
                .finalPrice(product.getFinalPrice())
                .subTotal(product.getFinalPrice() * quantity)
                .build();
        orderProducts.add(orderProduct);
    }
}
