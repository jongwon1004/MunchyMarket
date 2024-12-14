package com.munchymarket.MunchyMarket.controller.member;

import com.munchymarket.MunchyMarket.dto.member.MemberAddressDto;
import com.munchymarket.MunchyMarket.dto.member.MemberDto;
import com.munchymarket.MunchyMarket.dto.wrapper.ApiResponse;
import com.munchymarket.MunchyMarket.repository.member.MemberRepository;
import com.munchymarket.MunchyMarket.request.MemberLoginRequest;
import com.munchymarket.MunchyMarket.security.CustomMemberDetails;
import com.munchymarket.MunchyMarket.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberRepository memberRepository;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<MemberDto>> login(@AuthenticationPrincipal CustomMemberDetails customMemberDetails,
                                                        @ModelAttribute MemberLoginRequest memberLoginRequest) {

        Long memberId = customMemberDetails.getId();
        return ResponseEntity.ok(ApiResponse.ofSuccess(memberRepository.findMemberById(memberId)));
    }


    /**
     *  서버 연결 테스트 확인용
     *  접근 권한: ROLE_ADMIN
     */
    @GetMapping("/")
    public ResponseEntity<MemberAddressDto> getAllMembers() {
        return ResponseEntity.ok(memberRepository.findMemberAddressByMemberId(2L));
    }

}
