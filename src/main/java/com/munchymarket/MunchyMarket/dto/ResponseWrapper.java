package com.munchymarket.MunchyMarket.dto;

import java.util.List;

public record ResponseWrapper<T>(List<T> data) {
}
