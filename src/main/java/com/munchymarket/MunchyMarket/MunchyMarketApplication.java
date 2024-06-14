package com.munchymarket.MunchyMarket;

import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class MunchyMarketApplication {

	private final MemberRepository memberRepository;

	public static void main(String[] args) {
		SpringApplication.run(MunchyMarketApplication.class, args);
	}

	@PostConstruct
	public void init() {

		Member member = new Member().builder()
				.loginId("admin")
				.password("admin")
				.name("admin")
				.ruby("アドミン")
				.email("admin@gmail.com")
				.phoneNumber("010-1234-5678")
				.birth("1990-01-01")
				.role("ROLE_ADMIN")
				.build();

		memberRepository.save(member);
	}

}
