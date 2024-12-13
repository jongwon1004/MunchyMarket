package com.munchymarket.MunchyMarket.dto.wrapper;

import java.util.List;

public record ResponseWrapper<T>(List<T> data) {
}
