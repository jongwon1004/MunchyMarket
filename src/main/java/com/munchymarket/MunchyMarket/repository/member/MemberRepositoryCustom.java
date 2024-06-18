package com.munchymarket.MunchyMarket.repository.member;

import com.munchymarket.MunchyMarket.dto.MemberAddressDto;
import com.munchymarket.MunchyMarket.dto.MemberDto;
import com.munchymarket.MunchyMarket.request.LoginValidateCheckRequest;

import java.util.Map;

public interface MemberRepositoryCustom {

    Map<String, Object> findMemberByLoginIdOrEmail(LoginValidateCheckRequest loginValidateCheckRequest);
    MemberDto findMemberById(Long memberId);
    MemberAddressDto findMemberAddressByMemberId(Long memberId);
}
