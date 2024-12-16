package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.domain.Address;
import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.dto.member.MemberAddressDto;
import com.munchymarket.MunchyMarket.dto.member.MemberLoginResponseDto;
import com.munchymarket.MunchyMarket.dto.wrapper.ErrorCode;
import com.munchymarket.MunchyMarket.repository.member.MemberRepository;
import com.munchymarket.MunchyMarket.request.MemberLoginRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberLoginResponseDto login(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ErrorCode.DetailMessage.RESOURCE_NOT_FOUND, "会員", memberId)));

        return MemberLoginResponseDto.ToEntity(member);
    }

    public MemberAddressDto findMemberAddressByMemberId(Long memberId) {
        Member member = memberRepository.findMemberAddressByMemberId(memberId);
        Address address = member.getAddresses()
                .stream()
                .filter(Address::getIsBaseAddress) // baseAddress 가 true 인 주소
                .findFirst()
                .orElse(null);


        return MemberAddressDto.FromEntity(member, address);
    }
}
