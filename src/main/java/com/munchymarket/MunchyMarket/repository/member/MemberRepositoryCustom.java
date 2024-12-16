package com.munchymarket.MunchyMarket.repository.member;

import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.dto.member.MemberAddressDto;
import com.munchymarket.MunchyMarket.request.LoginValidateCheckRequest;

public interface MemberRepositoryCustom {

    Boolean findMemberByLoginIdOrEmail(String param, LoginValidateCheckRequest loginValidateCheckRequest);
    Member findMemberAddressByMemberId(Long memberId);
}
