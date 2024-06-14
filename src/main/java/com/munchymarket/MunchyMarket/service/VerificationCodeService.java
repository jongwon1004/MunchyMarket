package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.domain.VerificationCode;
import com.munchymarket.MunchyMarket.repository.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;

    public void saveCode(String phoneNumber, String verificationCode) {
        LocalDateTime now = LocalDateTime.now();
        verificationCodeRepository.save(new VerificationCode(phoneNumber, verificationCode, now, now.plusMinutes(5)));
    }


}
