package com.munchymarket.MunchyMarket.request;

import jakarta.validation.constraints.NotBlank;

public record PhoneVerificationRequest(@NotBlank String phoneNumber) {
}
