package com.munchymarket.MunchyMarket.controller.member;

import com.munchymarket.MunchyMarket.dto.member.MemberAddressDto;
import com.munchymarket.MunchyMarket.dto.member.join.SmsSendResponseDto;
import com.munchymarket.MunchyMarket.dto.wrapper.ApiResponse;
import com.munchymarket.MunchyMarket.dto.member.join.JoinRequest;
import com.munchymarket.MunchyMarket.request.LoginValidateCheckRequest;
import com.munchymarket.MunchyMarket.dto.member.join.SmsSendRequest;
import com.munchymarket.MunchyMarket.service.JoinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/members")
@RestController
public class JoinController {

    private final JoinService joinService;

    /**
     * 会員登録時ログインID、EMAILの重複チェック　→　Service階層でパラメータによって処理を分岐
     */
    @PostMapping("/verification")
    public ResponseEntity<ApiResponse<Map<String, String>>> verification(@RequestBody LoginValidateCheckRequest loginValidateCheckRequest) {
        return ResponseEntity.ok().body(ApiResponse.ofSuccess(joinService.validateCheck(loginValidateCheckRequest)));
    }


    /**
     * 会員登録時、SMS認証コードを送信する
     * 再送信された時を考慮して、前にあった認証コードDBを削除
     */
    @PostMapping("/sms")
    public ResponseEntity<ApiResponse<SmsSendResponseDto>> sms(@RequestBody SmsSendRequest smsSendRequest) {
        return ResponseEntity.ok(ApiResponse.ofSuccess(joinService.sendSMS(smsSendRequest.getPhoneNumber())));
    }

    /**
     * SMS認証コードの検証
     * @return true: 検証成功, false: 検証失敗
     */
    @PostMapping("/sms/verification")
    public ResponseEntity<ApiResponse<Map<String, String>>> smsVerification(@RequestBody Map<String, String> verificationCode) {

        Map<String, String> result = joinService.validateVerificationCode(verificationCode.get("phoneNumber"), verificationCode.get("code"));
        return ResponseEntity.ok().body(ApiResponse.ofSuccess(result));
    }


    /**
     * 最終的な会員登録Request
     * @param joinRequest　JoinExceptionHandlerでバリデーションチェック
     */
    @PostMapping
    public ResponseEntity<ApiResponse<MemberAddressDto>> join(@Valid @RequestBody JoinRequest joinRequest) {
        MemberAddressDto memberDto = joinService.join(joinRequest);
        return ResponseEntity.ok(ApiResponse.ofSuccess(memberDto));
    }
}
