package com.munchymarket.MunchyMarket.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AsyncTestDto {
    private Long couponId;
    private OrderDto order;
}