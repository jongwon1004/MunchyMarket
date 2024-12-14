package com.munchymarket.MunchyMarket.controller.product;

import com.munchymarket.MunchyMarket.dto.product.ReviewCreateDto;
import com.munchymarket.MunchyMarket.dto.wrapper.ApiResponse;
import com.munchymarket.MunchyMarket.security.CustomMemberDetails;
import com.munchymarket.MunchyMarket.service.ReviewService;
import com.munchymarket.MunchyMarket.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final JWTUtil jwtUtil;

    private final ReviewService reviewService;


    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, String>>> createReview(@ModelAttribute ReviewCreateDto reviewCreateDto,
                                                                         @AuthenticationPrincipal CustomMemberDetails customMemberDetails) {

        Long memberId = customMemberDetails.getId();
        reviewCreateDto.setMemberId(memberId);
        log.info("reviewCreateDto: {}", reviewCreateDto);

        reviewService.createReview(reviewCreateDto);
        return ResponseEntity.ok().body(ApiResponse.ofSuccess(Collections.singletonMap("message", "レビューが登録されました。")));
    }


}
