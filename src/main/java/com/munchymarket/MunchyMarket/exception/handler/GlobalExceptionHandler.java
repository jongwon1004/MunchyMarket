package com.munchymarket.MunchyMarket.exception.handler;

import com.munchymarket.MunchyMarket.dto.wrapper.ApiResponse;
import com.munchymarket.MunchyMarket.dto.wrapper.ErrorCode;
import com.munchymarket.MunchyMarket.exception.*;
import com.stripe.exception.StripeException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<Map<String, String>> handleValidationExceptions(Exception ex) {
        Map<String, String> errors = new HashMap<>();

        if (ex instanceof MethodArgumentNotValidException validException) {
            validException.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
            log.info("Validation error: {}", errors);
        } else if (ex instanceof IllegalArgumentException) {
            errors.put("error", ex.getMessage());
            log.info("Illegal argument error: {}", ex.getMessage());
        }

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(ProductRegisterException.class)
    public ResponseEntity<Map<String, Object>> handleCategoryNotFoundException(ProductRegisterException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("errors", ex.getErrors());
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(GcsFileUploadFailException.class)
    public ResponseEntity<ApiResponse<Void>> handleGcsFileUploadFailException(GcsFileUploadFailException ex) {
        return ResponseEntity.internalServerError().body(
                ApiResponse.ofFail(ex.getErrorCode(), ex.getMessage())
        );
    }


    @ExceptionHandler(DuplicateReviewException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateReviewException(DuplicateReviewException e) {
        return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
    }

    @ExceptionHandler(StripeException.class)
    public ResponseEntity<ApiResponse<Void>> handleStripeException(StripeException e) {
        log.error("StripeException occurred: {}", e.getMessage(), e);
        String detailedMessage = e.getMessage() != null ? e.getMessage() : ErrorCode.DetailMessage.SERVER_ERROR;

        return ResponseEntity.badRequest().body(ApiResponse.ofFail(ErrorCode.SERVER_ERROR, detailedMessage));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.ofFail(ErrorCode.RESOURCE_NOT_FOUND, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        log.error("Internal server error occurred", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.ofFail(ErrorCode.SERVER_ERROR, ErrorCode.DetailMessage.SERVER_ERROR));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        String allowedMethods = ex.getSupportedHttpMethods() != null
                ? ex.getSupportedHttpMethods().toString()
                : "なし";

        String errorMessage = String.format(
                ErrorCode.DetailMessage.METHOD_NOT_ALLOWED,
                ex.getMethod(),
                allowedMethods
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(ApiResponse.ofFail(ErrorCode.METHOD_NOT_ALLOWED, errorMessage));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        String errorMessage = String.format(
                ErrorCode.DetailMessage.METHOD_ARGUMENT_TYPE_MISMATCH,
                ex.getName(), // 요청 파라미터 이름
                ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "不明" // 기대 타입
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.ofFail(ErrorCode.METHOD_ARGUMENT_TYPE_MISMATCH, errorMessage));
    }

    @ExceptionHandler(TextBeltRequestException.class)
    public ResponseEntity<ApiResponse<Void>> handleTextBeltRequestException(TextBeltRequestException ex) {
        return ResponseEntity.status(ex.getErrorCode().getHttpStatusCode())
                .body(ApiResponse.ofFail(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(JoinRequestValidationException.class)
    public ResponseEntity<ApiResponse<Void>> handleJoinRequestValidationException(JoinRequestValidationException ex) {
        return ResponseEntity.status(ex.getErrorCode().getHttpStatusCode())
                .body(ApiResponse.ofFail(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFoundError(NoHandlerFoundException ex) {

        return ResponseEntity.status(ex.getStatusCode())
                .body(ApiResponse.ofFail(ErrorCode.NO_HANDLER_FOUND, ErrorCode.DetailMessage.NO_HANDLER_FOUND));
    }

    @ExceptionHandler({InvalidVerificationCodeException.class, VerificationCodeExpiredException.class})
    public ResponseEntity<ApiResponse<Void>> handleVerificationCodeException(RuntimeException ex) {
        ErrorCode errorCode = null;
        String message = null;

        if (ex instanceof InvalidVerificationCodeException) {
            InvalidVerificationCodeException exception = (InvalidVerificationCodeException) ex;
            errorCode = exception.getErrorCode();
            message = exception.getMessage();
        } else if (ex instanceof VerificationCodeExpiredException) {
            VerificationCodeExpiredException exception = (VerificationCodeExpiredException) ex;
            errorCode = exception.getErrorCode();
            message = exception.getMessage();
        }

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(ApiResponse.ofFail(errorCode, message));
    }



//    JwtFilter 의 handleExpiredJwtException 에서 처리
//    @ExceptionHandler(ExpiredJwtException.class)
//    public ResponseEntity<ApiResponse<Void>> handleExpiredJwtException(ExpiredJwtException ex) {
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(ApiResponse.ofFail(ErrorCode.TOKEN_EXPIRED, ErrorCode.DetailMessage.TOKEN_EXPIRED));
//    }


}
