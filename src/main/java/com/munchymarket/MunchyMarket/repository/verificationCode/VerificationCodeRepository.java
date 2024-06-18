package com.munchymarket.MunchyMarket.repository.verificationCode;

import com.munchymarket.MunchyMarket.domain.VerificationCode;
import com.munchymarket.MunchyMarket.domain.enums.StatusType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long>, VerificationCodeRepositoryCustom {
    long deleteByPhoneNumber(String phoneNumber);
    VerificationCode findByPhoneNumberAndCode(String phoneNumber, String code);
}
