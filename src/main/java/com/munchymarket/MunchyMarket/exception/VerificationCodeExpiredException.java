package com.munchymarket.MunchyMarket.exception;

import com.munchymarket.MunchyMarket.dto.wrapper.ErrorCode;
import lombok.Getter;

@Getter
public class VerificationCodeExpiredException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public VerificationCodeExpiredException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }


}
