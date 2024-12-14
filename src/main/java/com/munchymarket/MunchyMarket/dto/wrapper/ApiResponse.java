package com.munchymarket.MunchyMarket.dto.wrapper;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private boolean success;
    private T data;
    @JsonProperty("error_code")
    private String errorCode;
    @JsonProperty("error_message")
    private String errorMessage;

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
