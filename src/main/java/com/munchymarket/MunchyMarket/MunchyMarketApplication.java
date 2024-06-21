package com.munchymarket.MunchyMarket;

import com.munchymarket.MunchyMarket.domain.Address;
import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.domain.VerificationCode;
import com.munchymarket.MunchyMarket.domain.enums.Role;
import com.munchymarket.MunchyMarket.domain.enums.StatusType;
import com.munchymarket.MunchyMarket.repository.address.AddressRepository;
import com.munchymarket.MunchyMarket.repository.member.MemberRepository;
import com.munchymarket.MunchyMarket.repository.member.MemberRepositoryCustomImpl;
import com.munchymarket.MunchyMarket.repository.verificationCode.VerificationCodeRepository;
import com.munchymarket.MunchyMarket.request.JoinRequest;
import com.munchymarket.MunchyMarket.service.JoinService;
import com.munchymarket.MunchyMarket.service.VerificationCodeService;
import com.munchymarket.MunchyMarket.utils.PhoneNumberUtil;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jdk.jshell.Snippet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PersistContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
@Slf4j
public class MunchyMarketApplication {

	private final VerificationCodeService verificationCodeService;

	private final JoinService joinService;
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MunchyMarketApplication.class, args);
	}

	@PostConstruct
	public void init() {

		log.info("서버 실행됨 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		JoinRequest joinRequest = new JoinRequest("noboru1004", "hellonoboru!", "hellonoboru!","藤田　昇", "フジタ　ノボル", "noboru99@gmail.com",
				"08053275296", "男", "1990-11-11", "557-0012", "大阪府大阪市西成区南津守", "１ー１０−６ Hollywood Heights 101号室", "618553", true);

		log.info("joined member: {}", joinService.join(joinRequest));

//		VerificationCode verificationCode = verificationCodeService.saveCode(PhoneNumberUtil.phoneNumberFormat(joinRequest.getPhoneNumber()), joinRequest.getCode());
//		verificationCodeService.validateVerificationCode(verificationCode.getPhoneNumber(), verificationCode.getCode());


		Member admin = new Member("whddnjs3340", passwordEncoder.encode("helloworld!"), "choijongwon", "チェチョンウォン", "helloworld@gmail.com", PhoneNumberUtil.phoneNumberFormat("08045326353"),
				"男", "1992-12-30", "ROLE_ADMIN");

		memberRepository.save(admin);

	}

}
