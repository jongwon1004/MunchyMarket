package com.munchymarket.MunchyMarket;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@RequiredArgsConstructor
@Slf4j
public class MunchyMarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(MunchyMarketApplication.class, args);
	}


	@PostConstruct
	public void init() {

	}

}
