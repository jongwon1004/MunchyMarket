package com.munchymarket.MunchyMarket.dto.wrapper;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private boolean success;
    private T data;
    private String code;
    private String message;

    public static ApiResponse<Void> ofSuccess() {
        return new ApiResponse<>(true, null, null, null);
    }

    public static <T> ApiResponse<T> ofSuccess(T data) {
        return new ApiResponse<>(true, data, null, null);
    }

    public static ApiResponse<Void> ofFail(ErrorCode errorCode, String message) {
        return new ApiResponse<>(false, null, errorCode.getCode(), message);
    }
}
