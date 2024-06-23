package com.munchymarket.MunchyMarket.domain;

import com.munchymarket.MunchyMarket.domain.enums.StatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@ToString
public class VerificationCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_code_id")
    private Long id;

    @Column(nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "code", nullable = false, length = 6)
    private String code;

    @CreatedDate
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "expired_date")
    private LocalDateTime expiredDate;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    public VerificationCode(String phoneNumber, String code, LocalDateTime createdDate, LocalDateTime expiredDate, StatusType status) {
        this.phoneNumber = phoneNumber;
        this.code = code;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;
        this.status = status;
    }

    public void changeStatus(StatusType status) {
        this.status = status;
    }
}
