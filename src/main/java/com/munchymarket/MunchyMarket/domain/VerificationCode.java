package com.munchymarket.MunchyMarket.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class VerificationCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    @Column(name = "verification_code", length = 6)
    private String verificationCode;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

    public VerificationCode(String phoneNumber, String verificationCode, LocalDateTime createdDate, LocalDateTime expiredDate) {
        this.phoneNumber = phoneNumber;
        this.verificationCode = verificationCode;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;
    }
}
