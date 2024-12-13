package com.munchymarket.MunchyMarket.repository.member;

import com.munchymarket.MunchyMarket.dto.member.AddressDto;
import com.munchymarket.MunchyMarket.dto.member.MemberAddressDto;
import com.munchymarket.MunchyMarket.dto.member.MemberDto;
import com.munchymarket.MunchyMarket.request.LoginValidateCheckRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.munchymarket.MunchyMarket.domain.QAddress.address;
import static com.munchymarket.MunchyMarket.domain.QMember.member;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Boolean findMemberByLoginIdOrEmail(String param, LoginValidateCheckRequest loginValidateCheckRequest) {

        Boolean response = null;

        if (param.equals("loginId")) {
            response = queryFactory.selectFrom(member)
                    .where(member.loginId.eq(loginValidateCheckRequest.getLoginId()))
                    .fetchOne() == null;
        } else {
            response = queryFactory.selectFrom(member)
                    .where(member.email.eq(loginValidateCheckRequest.getEmail()))
                    .fetchOne() == null;
        }

        return response;
    }

    @Override
    public MemberDto findMemberById(Long memberId) {
        return queryFactory.select(
                        Projections.constructor(MemberDto.class,
                                member.id,
                                member.loginId,
                                member.name,
                                member.ruby,
                                member.email,
                                member.phoneNumber,
                                member.sex,
                                member.birth,
                                member.role)
                )
                .from(member)
                .where(member.id.eq(memberId))
                .fetchOne();
    }

    @Override
    public MemberAddressDto findMemberAddressByMemberId(Long memberId) {
        return queryFactory.select(
                        Projections.constructor(MemberAddressDto.class,
                                member.id,
                                member.loginId,
                                member.name,
                                member.ruby,
                                member.email,
                                member.phoneNumber,
                                member.sex,
                                member.birth,
                                member.role,
                                Projections.constructor(AddressDto.class,
                                        address.id,
                                        address.postalCode,
                                        address.regionAddress,
                                        address.detailAddress,
                                        address.isBaseAddress,
                                        address.createdDate,
                                        address.lastModifiedDate)
                        )
                )
                .from(member)
                .leftJoin(address).on(member.id.eq(address.member.id))
                .where(member.id.eq(memberId))
                .fetchOne();

    }
}
