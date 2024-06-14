package com.munchymarket.MunchyMarket;

import com.munchymarket.MunchyMarket.repository.MemberRepository;
import com.munchymarket.MunchyMarket.service.JoinService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmsSendTest {

    @Autowired
    JoinService joinService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void sendSMS() {
        // given
        String to = "08047830389";

        // when
        joinService.sendSMS(to);

        // then
    }

}
