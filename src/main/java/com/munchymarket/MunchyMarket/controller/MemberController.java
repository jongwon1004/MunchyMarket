package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.repository.MemberRepository;
import com.munchymarket.MunchyMarket.request.LoginIdCheckRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/")
    public ResponseEntity<List<Member>> getAllMembers() {
        return ResponseEntity.ok(memberRepository.findAll());
    }


    @GetMapping("/validate-login-id")
    public ResponseEntity<Map<String, Boolean>> validateLoginId(@RequestBody LoginIdCheckRequest loginId) {

        // 로그인 아이디 중복시 false, 중복이 아니면 true
        return ResponseEntity.ok(Collections.singletonMap("result", !memberRepository.existsByLoginId(loginId.getLoginId())));
    }
}
