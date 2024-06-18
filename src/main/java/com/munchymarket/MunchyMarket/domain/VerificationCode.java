package com.munchymarket.MunchyMarket.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.munchymarket.MunchyMarket.domain.enums.StatusType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@ToString
public class VerificationCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    @Column(name = "code", length = 6)
    private String code;

    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
