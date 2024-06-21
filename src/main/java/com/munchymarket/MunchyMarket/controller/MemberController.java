package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.dto.MemberAddressDto;
import com.munchymarket.MunchyMarket.repository.member.MemberRepository;
import com.munchymarket.MunchyMarket.request.MemberLoginRequest;
import com.munchymarket.MunchyMarket.security.CustomMemberDetails;
import com.munchymarket.MunchyMarket.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
public class MemberController {

    private final MemberRepository memberRepository;
    private final JWTUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@ModelAttribute MemberLoginRequest memberLoginRequest, HttpServletRequest request) {

        log.info("loginId = {}", memberLoginRequest);

//        // 인증된 유저만 Controller 로 오니까 이렇게 하려고 했는데 밑에 방법이 좀더 표준적인 방법이라고 함
//        CustomMemberDetails userDetails = (CustomMemberDetails) request.getAttribute("userDetails");
//        if (userDetails == null) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User details not found");
//        }

        // Spring Security 컨텍스트에서 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not authenticated");
        }

        CustomMemberDetails userDetails = (CustomMemberDetails) authentication.getPrincipal();

        Long memberId = userDetails.getId();
        log.info("memberId = {}", memberId);
        return ResponseEntity.ok(memberRepository.findMemberById(memberId));
    }

    @PostMapping("/role-check")
    public ResponseEntity<?> roleCheck(@RequestHeader String authorization) {

        log.info("authorization = {}", authorization);
        String token = authorization.substring(7);

        Long memberId = jwtUtil.extractMemberId(token);
        log.info("login memberId = {}", memberId);


        return ResponseEntity.ok("role check");

    }

    @GetMapping("/")
    public ResponseEntity<MemberAddressDto> getAllMembers() {
        return ResponseEntity.ok(memberRepository.findMemberAddressByMemberId(1L));
    }

}
