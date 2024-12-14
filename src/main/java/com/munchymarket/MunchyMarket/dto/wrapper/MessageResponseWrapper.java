package com.munchymarket.MunchyMarket.dto.wrapper;

import java.util.List;

public record MessageResponseWrapper<T>(String message, List<T> data) {
}

