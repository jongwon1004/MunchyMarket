package com.munchymarket.MunchyMarket.service;

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
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
@Service
public class JoinService {

    private final VerificationCodeService verificationCodeService;

    @Value("${textBelt.api.key}")
    private String apiKey;

    public void sendSMS(String to) {

        to = "+81" + to.substring(1);
        log.info("message sent to: {}", to);

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
        verificationCodeService.saveCode(to, String.valueOf(verificationCode));


    }

    private int createRandomNumber() {
        SecureRandom secureRandom = new SecureRandom();
        return 100000 + secureRandom.nextInt(900000);
    }

    public Boolean validateVerificationCode(String phoneNumber, String code) {
        return true;
    }
}
