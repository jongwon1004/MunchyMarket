package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.request.JoinRequest;
import com.munchymarket.MunchyMarket.request.SmsSendRequest;
import com.munchymarket.MunchyMarket.service.JoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/join")
@RestController
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/sendSMS")
    public ResponseEntity<Map<String, String>> sendSMS(@RequestBody SmsSendRequest smsSendRequest) {
        joinService.sendSMS(smsSendRequest.getPhoneNumber());
        return ResponseEntity.ok(Collections.singletonMap("result", "sendSMS success"));
    }

    @PostMapping("/validate-verification-code")
    public ResponseEntity<Map<String, Object>> validateVerificationCode(@RequestBody Map<String, String> verificationCode) {
        return ResponseEntity.ok(Collections.singletonMap("result", joinService.validateVerificationCode(verificationCode.get("phoneNumber"), verificationCode.get("code"))));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody JoinRequest joinRequest) {


        return ResponseEntity.ok("Registered");
    }
}
