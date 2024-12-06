package com.munchymarket.MunchyMarket.domain;

import jakarta.persistence.*;

@Table(name = "order_products")
@Entity
public class OrderProduct {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id", nullable = false, updatable = false)
    private Long id;
}
