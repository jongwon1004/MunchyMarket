package com.munchymarket.MunchyMarket.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "order_products")
@Entity
public class OrderProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    @Column(name = "base_price", nullable = false)
    private int basePrice;

    @Column(name = "final_price")
    private int finalPrice;

    @Column(name = "sub_total")
    private int subTotal;

    @Builder
    public OrderProduct(Order order, Product product, int basePrice, int quantity, int finalPrice, int subTotal) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.basePrice = basePrice;
        this.finalPrice = finalPrice;
        this.subTotal = subTotal;
    }
}
