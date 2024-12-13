package com.munchymarket.MunchyMarket.repository.member;

import com.munchymarket.MunchyMarket.dto.member.MemberAddressDto;
import com.munchymarket.MunchyMarket.dto.member.MemberDto;
import com.munchymarket.MunchyMarket.request.LoginValidateCheckRequest;

public interface MemberRepositoryCustom {

    Boolean findMemberByLoginIdOrEmail(String param, LoginValidateCheckRequest loginValidateCheckRequest);
    MemberDto findMemberById(Long memberId);
    MemberAddressDto findMemberAddressByMemberId(Long memberId);
}
