package com.munchymarket.MunchyMarket.controller;

import com.munchymarket.MunchyMarket.dto.MemberAddressDto;
import com.munchymarket.MunchyMarket.request.JoinRequest;
import com.munchymarket.MunchyMarket.request.LoginValidateCheckRequest;
import com.munchymarket.MunchyMarket.request.SmsSendRequest;
import com.munchymarket.MunchyMarket.service.JoinService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class JoinController {

    private final JoinService joinService;

    /**
     * 会員登録時ログインID、EMAILの重複チェック　→　Service階層でパラメータによって処理を分岐
     * @return true: 重複なし, false: 重複あり
     */
    @PostMapping("/validate")
    public ResponseEntity<Map<String, Object>> validate(@RequestBody LoginValidateCheckRequest loginValidateCheckRequest) {
        Map<String, Object> responseMap = joinService.validateCheck(loginValidateCheckRequest);
        Boolean result = (Boolean) responseMap.get("result");
        return result ? ResponseEntity.ok(responseMap) : ResponseEntity.status(HttpStatus.CONFLICT).body(responseMap);
    }


    /**
     * 会員登録時、SMS認証コードを送信する
     * 再送信された時を考慮して、前にあった認証コードDBを削除
     * TODO: SMS送信エラー時の処理を追加すること
     */
    @PostMapping("/send-sms")
    public ResponseEntity<Map<String, String>> sendSMS(@RequestBody SmsSendRequest smsSendRequest) {
        joinService.sendSMS(smsSendRequest.getPhoneNumber());
        Map<String, String> response = new HashMap<>();
        response.put("result", "sendSMS success");
        response.put("phoneNumber", smsSendRequest.getPhoneNumber());
        return ResponseEntity.ok(response);
    }

    /**
     * SMS認証コードの検証
     * @return true: 検証成功, false: 検証失敗
     */
    @PostMapping("/verification-code")
    public ResponseEntity<Map<String, Object>> validateVerificationCode(@RequestBody Map<String, String> verificationCode) {
        Map<String, Object> response
                = new HashMap<>(
                joinService.validateVerificationCode(verificationCode.get("phoneNumber"), verificationCode.get("code"))
        );

        return (Boolean) response.get("result") ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }


    /**
     * 最終的な会員登録Request
     * @param joinRequest　JoinExceptionHandlerでバリデーションチェック
     * @return
     */
    @PostMapping("/join")
    public ResponseEntity<Map<String, Object>> join(@Valid @RequestBody JoinRequest joinRequest) {

        log.info("Join request: {}", joinRequest);
        Map<String, Object> response = new HashMap<>();

        // VerificationCode 테이블에 STATUS 가 SUCCESS 인지 다시한번 검증
        // Status 가 SUCCESS 가 아니면 인증을 아직 받지 않은 것으로 판단
        // statusCheck 메소드에서 인증이 완료되면 DB에서 해당 인증정보를 삭제
        if (!joinService.statusCheck(joinRequest.getPhoneNumber(), joinRequest.getCode())
                || !joinRequest.getSmsVerified()) {
            response.put("message", "SMS認証がまだ完了してない状態です。再度認証を行ってください");
            return ResponseEntity.badRequest().body(response);
        }

        MemberAddressDto memberDto = joinService.join(joinRequest);
        response.put("message", "会員登録が完了しました");
        response.put("member", memberDto);
        return ResponseEntity.ok(response);
    }
}
