package com.munchymarket.MunchyMarket.dto.wrapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    // CLIENT
    URL_NOT_FOUND(HttpStatus.NOT_FOUND, "C001"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002"),
    METHOD_ARGUMENT_TYPE_MISMATCH(BAD_REQUEST, "C003"),
    MISSING_REQUEST_PARAMETER(BAD_REQUEST, "C004"),
    MEDIA_TYPE_NOT_SUPPORTED(UNSUPPORTED_MEDIA_TYPE, "C005"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "C010"),
    SC_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "C011"),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "C013"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "C014"),



    // SERVER
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "S001");

    private final HttpStatus httpStatusCode;
    private final String code;

    ErrorCode(HttpStatus httpStatusCode, String code) {
        this.httpStatusCode = httpStatusCode;
        this.code = code;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class DetailMessage {


        // CLIENT
        public static final String METHOD_NOT_ALLOWED = "%s : 해당 HTTP 메소드는 지원되지 않습니다. 허용 메소드 : %s";
        public static final String METHOD_ARGUMENT_TYPE_MISMATCH = "요청 파라미터에서 %s 값은 %s 타입이어야 합니다.";
        public static final String MISSING_REQUEST_PARAMETER = "요청 파라미터에서 %s 값은 필수입니다.";
        public static final String MEDIA_TYPE_NOT_SUPPORTED = "%s : 지원하지 않는 media type 입니다. 지원 type : %s";
        public static final String RESOURCE_NOT_FOUND = "該当する 『 %s 』 がありません: %s";
        public static final String SC_UNAUTHORIZED = "このリソースにアクセスする権限がありません";
        public static final String LOGIN_FAILED = "パスワードまたはログインIDが間違っています";
        public static final String TOKEN_EXPIRED = "トークンの有効期限が切れています";






        // SERVER
        public static final String SERVER_ERROR = "서버 내부에 문제가 생겼습니다.";
    }
}