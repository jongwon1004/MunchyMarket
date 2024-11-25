package com.munchymarket.MunchyMarket.repository.member;

import com.munchymarket.MunchyMarket.domain.Address;
import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.dto.MemberAddressDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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