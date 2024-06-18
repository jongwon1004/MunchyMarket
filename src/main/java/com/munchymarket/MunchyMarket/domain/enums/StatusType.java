package com.munchymarket.MunchyMarket.domain.enums;

import lombok.Getter;

@Getter
public enum StatusType {
    UNVERIFIED("인증 전"),
    SUCCESS("인증에 성공했습니다.");
    private final String message;

    StatusType(String message) {
        this.message = message;
    }
}
