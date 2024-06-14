package com.munchymarket.MunchyMarket.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SmsSendRequest {

    @NotBlank(message = "電話番号を入力してください")
    private String phoneNumber;
}
