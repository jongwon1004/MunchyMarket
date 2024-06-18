package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.domain.VerificationCode;
import com.munchymarket.MunchyMarket.domain.enums.StatusType;
import com.munchymarket.MunchyMarket.repository.verificationCode.VerificationCodeRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class VerificationCodeService {

    private final VerificationCodeRepository verificationCodeRepository;
    private final EntityManager em;

    @Transactional
    public long deleteByPhoneNumber(String phoneNumber) {
        return verificationCodeRepository.deleteByPhoneNumber(phoneNumber);
    }

    @Transactional
    public VerificationCode saveCode(String phoneNumber, String code) {
        LocalDateTime now = LocalDateTime.now();
        return verificationCodeRepository.save( new VerificationCode(phoneNumber, code, now, now.plusMinutes(5), StatusType.UNVERIFIED));
    }


    @Transactional
    public Map<String, Object> validateVerificationCode(String phoneNumber, String code) {
        VerificationCode result = verificationCodeRepository.validateVerificationCode(phoneNumber, code);

        Map<String, Object> response = new HashMap<>();

        // null 일 경우는 해당 번호의 인증번호가 없다는 뜻
        if (result != null) {
            // 만료일이 현재 시간보다 이후인지 확인
            if (result.getExpiredDate().isBefore(LocalDateTime.now())) {
                response.put("result", false);
                response.put("message", "認証番号の有効期限が切れました。再度認証番号を発行してください");
            } else {
                // 만료일이 안지났고 인증번호가 일치하면 Status 를 SUCCESS 로 변경
                // 회원가입 최종단계 에서 인증번호가 인증되었는지 유효성 검사를 하고 Status 확인 후 삭제
                result.changeStatus(StatusType.SUCCESS); // Dirty Checking
                response.put("result", true);
                response.put("message", "認証に成功しました");
            }
        } else {
            response.put("result", false);
            response.put("message", "認証番号が一致しません。再度確認してください");
        }
        return response;
    }

    public VerificationCode statusCheck(String phoneNumber, String code) {
        return verificationCodeRepository.findByPhoneNumberAndCode(phoneNumber, code);
    }
}
