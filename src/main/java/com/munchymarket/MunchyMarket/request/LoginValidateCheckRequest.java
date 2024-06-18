package com.munchymarket.MunchyMarket.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class LoginValidateCheckRequest {
    private String loginId;
    private String email;
}
