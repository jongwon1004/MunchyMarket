package com.munchymarket.MunchyMarket.service;

import com.munchymarket.MunchyMarket.domain.Address;
import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.domain.VerificationCode;
import com.munchymarket.MunchyMarket.domain.enums.StatusType;
import com.munchymarket.MunchyMarket.dto.MemberAddressDto;
import com.munchymarket.MunchyMarket.repository.address.AddressRepository;
import com.munchymarket.MunchyMarket.repository.member.MemberRepository;
import com.munchymarket.MunchyMarket.request.JoinRequest;
import com.munchymarket.MunchyMarket.request.LoginValidateCheckRequest;
import com.munchymarket.MunchyMarket.utils.PhoneNumberUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
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

    public Map<String, Object> validateCheck(LoginValidateCheckRequest loginValidateCheckRequest) {

        Map<String, Object> response = new HashMap<>();
        String param = null;

        if (loginValidateCheckRequest.getLoginId() != null) {
            param = "loginId";
            if (!loginValidateCheckRequest.getLoginId().matches("^(?=.*\\d)[a-zA-Z\\d]{8,20}$")) {
                response.put("result", false);
                response.put("message", "ログインIDは英字と数字を含む8文字以上20文字以下で入力してください");
                return response;
            } else {
                return buildResponse(memberRepository.findMemberByLoginIdOrEmail(param, loginValidateCheckRequest), param);
            }

        } else if (loginValidateCheckRequest.getEmail() != null) {
            param = "email";

            if (!loginValidateCheckRequest.getEmail().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                response.put("result", false);
                response.put("message", "メールアドレスの形式で入力してください");
                return response;
            } else {
                return buildResponse(memberRepository.findMemberByLoginIdOrEmail(param, loginValidateCheckRequest), param);
            }
        }

        response.put("result", false);
        response.put("message", "エラーが発生しました、再度お試しください");
        return response;
    }


    private Map<String, Object> buildResponse(boolean notExist, String param) {
        Map<String, Object> response = new HashMap<>();
        if (notExist) {
            response.put("result", true);
            response.put("message", param.equals("loginId") ? "使用可能なログインIDです" : "使用可能なEMAILです");
        } else {
            response.put("result", false);
            response.put("message", param.equals("loginId") ? "既に登録されているログインIDです" : "既に登録されているEMAILです");
        }
        return response;
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

        Address memberAddress = new Address().builder()
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
