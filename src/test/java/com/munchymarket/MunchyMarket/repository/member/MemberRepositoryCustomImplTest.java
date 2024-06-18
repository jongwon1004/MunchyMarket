package com.munchymarket.MunchyMarket.repository.member;

import com.munchymarket.MunchyMarket.dto.MemberAddressDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
class MemberRepositoryCustomImplTest {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MemberRepositoryCustomImplTest.class);
    @Autowired
    MemberRepository memberRepository;


    @Test
    void findMemberByIdToDto() {
        MemberAddressDto memberByIdToDto = memberRepository.findMemberAddressByMemberId(1L);
        System.out.println("memberByIdToDto = " + memberByIdToDto);
    }

}