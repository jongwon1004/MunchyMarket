package com.munchymarket.MunchyMarket.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munchymarket.MunchyMarket.dto.wrapper.ApiResponse;
import com.munchymarket.MunchyMarket.dto.wrapper.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Java 객체를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonResponse = objectMapper.writeValueAsString(
                ApiResponse.ofFail(ErrorCode.SC_UNAUTHORIZED, ErrorCode.DetailMessage.SC_UNAUTHORIZED)
        );

        response.getWriter().write(jsonResponse);
    }
}
