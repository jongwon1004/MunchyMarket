package com.munchymarket.MunchyMarket.repository.verificationCode;

import com.munchymarket.MunchyMarket.domain.VerificationCode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;

import static com.munchymarket.MunchyMarket.domain.QVerificationCode.*;

public class VerificationCodeRepositoryCustomImpl implements VerificationCodeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public VerificationCodeRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public VerificationCode validateVerificationCode(String phoneNumber, String code) {

        // 해당 번호와 인증번호가 존재하는지 확인
        return queryFactory
                .selectFrom(verificationCode)
                .where(verificationCode.phoneNumber.eq(phoneNumber)
                        .and(verificationCode.code.eq(code))
                        .and(verificationCode.expiredDate.after(LocalDateTime.now())))
                .fetchOne();
    }

}
