package com.munchymarket.MunchyMarket.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Role {
    ROLE_ADMIN("ROLE_ADMIN", "管理者"),
    ROLE_USER("ROLE_USER", "会員");

    private final String key;
    private final String name;

    public static Role fromKey(String key) {
        for (Role role : Role.values()) {
            if (role.getKey().equals(key)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No role with key: " + key);
    }
}


