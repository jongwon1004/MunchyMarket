package com.munchymarket.MunchyMarket.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginValidateCheckRequest {

    private String loginId;
    private String email;
}
