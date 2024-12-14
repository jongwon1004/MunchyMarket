package com.munchymarket.MunchyMarket.utils;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    private static final Logger log = LoggerFactory.getLogger(JWTUtil.class);
    private final SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = new SecretKeySpec(
                secret.toString().getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
        System.out.println("secretKey = " + secretKey);
    }


    public Long getId(String token) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7).trim(); // "Bearer " 제거 후 공백 제거
        }

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", Long.class);
    }

    public String getLoginId(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("loginId", String.class);
    }


    public String getRole(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    public Boolean isExpired(String token) {

        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }


    public String createJwt(Long id, String loginId, String role) {

        ZonedDateTime nowInJst = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));// 일본 시간대 설정
        ZonedDateTime expiredMs = nowInJst.plusHours(24);


        return Jwts.builder()
                .claim("id", id)
                .claim("loginId", loginId)
                .claim("role", role)
                .issuedAt(Date.from(nowInJst.toInstant()))
                .expiration(Date.from(expiredMs.toInstant()))
                .signWith(secretKey)
                .compact();
    }
}