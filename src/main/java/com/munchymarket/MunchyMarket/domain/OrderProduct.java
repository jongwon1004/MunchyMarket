package com.munchymarket.MunchyMarket.domain;

import jakarta.persistence.*;

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

    @Column(name = "discounted_price")
    private int discountedPrice;

    @Column(name = "sub_total")
    private int subTotal;

}
