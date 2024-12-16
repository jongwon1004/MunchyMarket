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
    LOGIN_ID_INVALID(BAD_REQUEST, "C016"),
    LOGIN_ID_EXISTS(CONFLICT, "C017"),
    EMAIL_INVALID(BAD_REQUEST, "C018"),
    EMAIL_EXISTS(CONFLICT, "C019"),
    NO_HANDLER_FOUND(BAD_REQUEST, "C020"),
    SMS_AUTH_NOT_COMPLETED(BAD_REQUEST, "C021"),
    VERIFICATION_CODE_EXPIRED(BAD_REQUEST, "C022"),
    INVALID_VERIFICATION_CODE(BAD_REQUEST, "S023"),



    // SERVER
    SERVER_ERROR(INTERNAL_SERVER_ERROR, "S001"),
    GCS_FILE_UPLOAD_ERROR(INTERNAL_SERVER_ERROR, "S002"),
    TEXT_BELT_REQUEST_FAILURE(INTERNAL_SERVER_ERROR, "S003"),
    TEXT_BELT_REQUEST_ERROR(INTERNAL_SERVER_ERROR, "S004");

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

        public static final String LOGIN_ID_INVALID = "ログインIDは英字と数字を含む8文字以上20文字以下で入力してください";
        public static final String LOGIN_ID_EXISTS = "既に登録されているログインIDです";
        public static final String EMAIL_INVALID = "メールアドレスの形式で入力してください";
        public static final String EMAIL_EXISTS = "既に登録されているメールアドレスです";



        // SERVER
        public static final String SERVER_ERROR = "서버 내부에 문제가 생겼습니다.";
        public static final String GCS_FILE_UPLOAD_ERROR = "GCSファイルアップロードに失敗しました";
        public static final String NO_HANDLER_FOUND = "指定されたAPIは存在しません。";
        public static final String SMS_AUTH_NOT_COMPLETED = "SMS認証がまだ完了してない状態です。再度認証を行ってください。";
        public static final String TEXT_BELT_REQUEST_FAILURE = "TextBelt リクエスト失敗";
        public static final String TEXT_BELT_REQUEST_ERROR = "TextBelt API リクエスト中にエラーが発生しました";
        public static final String VERIFICATION_CODE_EXPIRED = "認証番号の有効期限が切れました。再度認証番号を発行してください";
        public static final String INVALID_VERIFICATION_CODE = "認証番号が一致しません。再度確認してください";




    }
}