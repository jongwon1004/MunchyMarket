package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.domain.Address;
import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.domain.VerificationCode;
import com.munchymarket.MunchyMarket.domain.enums.StatusType;
import com.munchymarket.MunchyMarket.dto.member.MemberAddressDto;
import com.munchymarket.MunchyMarket.dto.wrapper.ErrorCode;
import com.munchymarket.MunchyMarket.exception.JoinRequestValidationException;
import com.munchymarket.MunchyMarket.repository.address.AddressRepository;
import com.munchymarket.MunchyMarket.repository.member.MemberRepository;
import com.munchymarket.MunchyMarket.request.JoinRequest;
import com.munchymarket.MunchyMarket.request.LoginValidateCheckRequest;
import com.munchymarket.MunchyMarket.utils.PhoneNumberUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.hibernate.mapping.Join;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class JoinService {

    private final VerificationCodeService verificationCodeService;

    private final MemberRepository memberRepository;
    private final AddressRepository addressRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${textBelt.api.key}")
    private String apiKey;

    public void sendSMS(String to) {

        to = PhoneNumberUtil.phoneNumberFormat(to);
        log.info("message sent to: {}", to);

        // 인증번호를 다시 보냈을 경우에 대비해 전에 있던 인증번호 DB를 삭제
        long deleteCount = verificationCodeService.deleteByPhoneNumber(to);
        log.info("delete count: {}", deleteCount);

        int verificationCode = createRandomNumber();

        try {
            final NameValuePair[] data = {
                    new BasicNameValuePair("phone", to), // 수신자 전화번호
                    new BasicNameValuePair("message", "MunchMarket\n[" + verificationCode + "]\n認証番号を入力してください。"), // 메시지 내용
                    new BasicNameValuePair("key", apiKey)
            };

            HttpClient httpClient = HttpClients.createMinimal();
            HttpPost httpPost = new HttpPost("https://textbelt.com/text");
            httpPost.setEntity(new UrlEncodedFormEntity(Arrays.asList(data), StandardCharsets.UTF_8));

            // 요청 전송 및 응답 처리
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String responseString = EntityUtils.toString(httpResponse.getEntity());
            JSONObject response = new JSONObject(responseString);

            System.out.println(response.toString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 인증번호 발신 완료후 DB에 저장
        VerificationCode savedVerificationCode = verificationCodeService.saveCode(to, String.valueOf(verificationCode));
        log.info("認証番号が保存されました: {}", savedVerificationCode);
    }

    private int createRandomNumber() {
        SecureRandom secureRandom = new SecureRandom();
        return 100000 + secureRandom.nextInt(900000);
    }

    public Map<String, Object> validateVerificationCode(String phoneNumber, String code) {
        return verificationCodeService.validateVerificationCode(PhoneNumberUtil.phoneNumberFormat(phoneNumber), code);
    }


    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class JoinResponse {
        public static final String LOGIN_ID_AVAILABLE_MESSAGE = "使用可能なログインIDです";
        public static final String EMAIL_AVAILABLE_MESSAGE = "使用可能なEMAILです";
    }

    private String validateLoginId(String loginId) {
        if (!loginId.matches("^(?=.*\\d)[a-zA-Z\\d]{8,20}$")) {
            throw new JoinRequestValidationException(ErrorCode.LOGIN_ID_INVALID, ErrorCode.DetailMessage.LOGIN_ID_INVALID);
        }

        if (memberRepository.existsByLoginId(loginId)) {
            throw new JoinRequestValidationException(ErrorCode.LOGIN_ID_EXISTS, ErrorCode.DetailMessage.LOGIN_ID_EXISTS);
        }
        return JoinResponse.LOGIN_ID_AVAILABLE_MESSAGE;
    }

    private String validateEmail(String email) {
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new JoinRequestValidationException(ErrorCode.EMAIL_INVALID, ErrorCode.DetailMessage.EMAIL_INVALID);
        }

        if (memberRepository.existsByEmail(email)) {
            throw  new JoinRequestValidationException(ErrorCode.EMAIL_EXISTS, ErrorCode.DetailMessage.EMAIL_EXISTS);
        }
        return JoinResponse.EMAIL_AVAILABLE_MESSAGE;
    }


    public Map<String, String> validateCheck(LoginValidateCheckRequest loginValidateCheckRequest) {

        Map<String, String> result = new HashMap<>();

        if (loginValidateCheckRequest.getLoginId() != null) {
            result.put("message", validateLoginId(loginValidateCheckRequest.getLoginId()));
            result.put("login_id", loginValidateCheckRequest.getLoginId());
        } else if (loginValidateCheckRequest.getEmail() != null) {
            result.put("message", validateEmail(loginValidateCheckRequest.getEmail()));
            result.put("email", loginValidateCheckRequest.getEmail());
        } else {
            throw new JoinRequestValidationException(ErrorCode.SERVER_ERROR, ErrorCode.DetailMessage.SERVER_ERROR);
        }

        return result;
    }



    public Boolean statusCheck(String phoneNumber, String code) {
        phoneNumber = PhoneNumberUtil.phoneNumberFormat(phoneNumber);
        log.info("phoneNumber: {}, code: {}", phoneNumber, code);
        VerificationCode verificationCode = verificationCodeService.statusCheck(phoneNumber, code);
        if(verificationCode.getStatus() == StatusType.SUCCESS) {
            verificationCodeService.deleteByPhoneNumber(phoneNumber);
            return true;
        } else return false;
    }

    @Transactional
    public MemberAddressDto join(JoinRequest joinRequest) {
        String encodedPass = passwordEncoder.encode(joinRequest.getPassword());
        joinRequest.changePassword(encodedPass);

        Member savedMember = memberRepository.save(joinRequest.toEntity());
        log.info("member joined: {}", savedMember);

        Address memberAddress = Address.builder()
                .member(savedMember)
                .postalCode(joinRequest.getPostalCode())
                .regionAddress(joinRequest.getRegionAddress())
                .detailAddress(joinRequest.getDetailAddress())
                .isBaseAddress(true)
                .build();
        log.info("member address: {}", memberAddress);

        addressRepository.save(memberAddress);

        return memberRepository.findMemberAddressByMemberId(savedMember.getId());
    }
}
