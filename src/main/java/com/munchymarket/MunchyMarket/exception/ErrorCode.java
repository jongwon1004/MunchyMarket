package com.munchymarket.MunchyMarket.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PACKAGING_TYPE_NOT_FOUND("該当する包装タイプがありません: %s"),
    CATEGORY_NOT_FOUND("該当するカテゴリがありません: %s"),
    GCS_FILE_UPLOAD_ERROR("GCSファイルアップロードに失敗しました");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String formatMessage(Object... args) {
        return String.format(message, args);
    }
}
