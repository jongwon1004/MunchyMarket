package com.munchymarket.MunchyMarket.repository.verificationCode;

import com.munchymarket.MunchyMarket.domain.VerificationCode;
import com.munchymarket.MunchyMarket.domain.enums.StatusType;


public interface VerificationCodeRepositoryCustom {

    VerificationCode validateVerificationCode(String phoneNumber, String code);

}
