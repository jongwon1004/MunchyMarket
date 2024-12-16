package com.munchymarket.MunchyMarket.exception;

import com.munchymarket.MunchyMarket.dto.wrapper.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidVerificationCodeException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public InvalidVerificationCodeException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
