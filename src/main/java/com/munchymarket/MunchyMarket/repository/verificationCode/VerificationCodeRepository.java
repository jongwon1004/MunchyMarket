package com.munchymarket.MunchyMarket.repository;

import com.munchymarket.MunchyMarket.domain.VerificationCode;
import com.munchymarket.MunchyMarket.repository.verificationCode.VerificationCodeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long>, VerificationCodeRepositoryCustom {

}
