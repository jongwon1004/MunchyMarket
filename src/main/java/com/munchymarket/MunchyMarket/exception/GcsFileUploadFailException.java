package com.munchymarket.MunchyMarket.exception;

public class GcsFileUploadFailException extends RuntimeException {

    public GcsFileUploadFailException(String message) {
        super(message);
    }

    public GcsFileUploadFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
