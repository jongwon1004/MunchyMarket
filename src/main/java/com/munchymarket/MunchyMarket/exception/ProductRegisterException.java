package com.munchymarket.MunchyMarket.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductRegisterException extends RuntimeException {

    private final List<String> errors;

    public ProductRegisterException(List<String> errors) {
        super("Multiple validation errors occurred");
        this.errors = errors;
    }
}
