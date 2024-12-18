package com.munchymarket.MunchyMarket.repository.member;

import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.dto.member.MemberAddressDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Test
//    @Commit
    void passwordEncoder() {

        Member member = memberRepository.findById(1L).get();
        member.changePassword(passwordEncoder.encode("password"));
        memberRepository.save(member);

    }
}