package com.munchymarket.MunchyMarket.repository.member;

import com.munchymarket.MunchyMarket.dto.AddressDto;
import com.munchymarket.MunchyMarket.dto.MemberAddressDto;
import com.munchymarket.MunchyMarket.dto.MemberDto;
import com.munchymarket.MunchyMarket.request.LoginValidateCheckRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static com.munchymarket.MunchyMarket.domain.QAddress.*;
import static com.munchymarket.MunchyMarket.domain.QMember.member;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Map<String, Object> findMemberByLoginIdOrEmail(LoginValidateCheckRequest loginValidateCheckRequest) {
        Map<String, Object> response = new HashMap<>();

        if (loginValidateCheckRequest.getLoginId() != null) {
            if (!loginValidateCheckRequest.getLoginId().matches("^(?=.*\\d)[a-zA-Z\\d]{8,20}$")) {
                response.put("result", false);
                response.put("message", "ログインIDは英字と数字を含む8文字以上20文字以下で入力してください");
                return response;
            }

            boolean notExist = queryFactory.selectFrom(member)
                    .where(member.loginId.eq(loginValidateCheckRequest.getLoginId()))
                    .fetchOne() == null;

            response.putAll(buildResponse(notExist, "loginId"));

        } else if (loginValidateCheckRequest.getEmail() != null) {

            if (!loginValidateCheckRequest.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                response.put("result", false);
                response.put("message", "メールアドレスの形式で入力してください");
                return response;
            }

            boolean notExist = queryFactory.selectFrom(member)
                    .where(member.email.eq(loginValidateCheckRequest.getEmail()))
                    .fetchOne() == null;

            response.putAll(buildResponse(notExist, "email"));
        }

        return response;
    }

    // 여기 서비스계층으로 옮겨야됨
    private Map<String, Object> buildResponse(boolean notExist, String param) {
        Map<String, Object> response = new HashMap<>();
        if (notExist) {
            if (param.equals("loginId")) {
                response.put("result", true);
                response.put("message", "使用可能なログインIDです");
            } else {
                response.put("result", true);
                response.put("message", "使用可能なEMAILです");
            }
        } else {
            if (param.equals("loginId")) {
                response.put("result", false);
                response.put("message", "既に登録されているログインIDです");
            } else {
                response.put("result", false);
                response.put("message", "既に登録されているEMAILです");
            }
        }
        return response;
    }

    @Override
    public MemberDto findMemberById(Long memberId) {
        return queryFactory.select(
                        Projections.constructor(MemberDto.class,
                                member.id.longValue(),
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
