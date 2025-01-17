package com.munchymarket.MunchyMarket.exception;

import com.munchymarket.MunchyMarket.dto.wrapper.ErrorCode;
import lombok.Getter;

@Getter
public class GcsFileUploadFailException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String message;

    public GcsFileUploadFailException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
    }
}
