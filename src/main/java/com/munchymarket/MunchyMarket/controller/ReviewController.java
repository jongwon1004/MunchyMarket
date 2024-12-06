package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.dto.ReviewCreateDto;
import com.munchymarket.MunchyMarket.service.ReviewService;
import com.munchymarket.MunchyMarket.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/review")
public class ReviewController {

    private final JWTUtil jwtUtil;

    private final ReviewService reviewService;


    @PostMapping
    public ResponseEntity<?> createReview(@ModelAttribute ReviewCreateDto reviewCreateDto, @RequestHeader("Authorization") String tk) {

        reviewCreateDto.setMemberId(jwtUtil.getId(tk));

        log.info("reviewCreateDto: {}", reviewCreateDto);
        reviewService.createReview(reviewCreateDto);
        return ResponseEntity.ok().body("reviewCreateDto");
    }

}
