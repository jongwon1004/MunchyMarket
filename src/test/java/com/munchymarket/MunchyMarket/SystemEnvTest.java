package com.munchymarket.MunchyMarket;

import org.junit.jupiter.api.Test;

public class SystemEnvTest {

    @Test
    void getEnv() {
        System.getenv().forEach((key, value) -> {
            System.out.println(key + " : " + value);
        });
    }
}
