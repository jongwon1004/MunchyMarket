package com.munchymarket.MunchyMarket.exception;

import com.munchymarket.MunchyMarket.dto.wrapper.ErrorCode;
import lombok.Getter;

@Getter
public class JoinRequestValidationException extends RuntimeException {
    private final ErrorCode errorCode;
    private final String message;

    public JoinRequestValidationException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
