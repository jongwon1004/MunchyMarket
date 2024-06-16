package com.munchymarket.MunchyMarket;

import com.munchymarket.MunchyMarket.domain.Address;
import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.repository.address.AddressRepository;
import com.munchymarket.MunchyMarket.repository.member.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
public class MunchyMarketApplication {

	private final MemberRepository memberRepository;
	private final AddressRepository addressRepository;

	private final BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(MunchyMarketApplication.class, args);
	}

	@PostConstruct
	public void init() {

		Member member = new Member().builder()
				.loginId("admin1004")
				.password(passwordEncoder.encode("adminPassword1004"))
				.name("CHOI BORU")
				.ruby("チエ　ボル")
				.email("admin@gmail.com")
				.phoneNumber("080-5327-5296")
				.sex("男")
				.birth("1990-11-11")
				.role("ROLE_ADMIN")
				.build();
		memberRepository.save(member);

		Address address = new Address().builder()
				.member(member)
				.postalCode("557-0012")
				.regionAddress("大阪府大阪市西成区南津守")
				.detailAddress("１ー１０−６ Hollywood Heights 101号室")
				.isBaseAddress(true)
				.build();

		addressRepository.save(address);


	}

}
