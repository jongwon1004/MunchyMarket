package com.munchymarket.MunchyMarket;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class ToCamelCase {

    @Test
    void toCamelCase() throws IOException {

        String snakeCase = "base_price_hello";

        String[] parts = snakeCase.split("_"); // {"base", "price"}
        StringBuilder camelCase = new StringBuilder(parts[0]); // base
        for (int i = 1; i < parts.length; i++) {
            camelCase.append(parts[i].substring(0, 1).toUpperCase()) // 첫 글자 대문자
                    .append(parts[i].substring(1));
        }
        System.out.println(camelCase);
    }
}
