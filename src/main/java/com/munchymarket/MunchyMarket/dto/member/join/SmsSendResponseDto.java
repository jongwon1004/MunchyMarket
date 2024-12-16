package com.munchymarket.MunchyMarket.dto.member.join;

import com.munchymarket.MunchyMarket.domain.VerificationCode;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmsSendResponseDto {

    private String phoneNumber;
    private LocalDateTime createdDate;
    private LocalDateTime expiredDate;

    public static SmsSendResponseDto fromEntity(VerificationCode verificationCode) {
        return SmsSendResponseDto.builder()
                .phoneNumber(verificationCode.getPhoneNumber())
                .createdDate(verificationCode.getCreatedDate())
                .expiredDate(verificationCode.getExpiredDate())
                .build();
    }
}
