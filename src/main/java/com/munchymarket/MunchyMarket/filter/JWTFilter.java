package com.munchymarket.MunchyMarket.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.dto.wrapper.ApiResponse;
import com.munchymarket.MunchyMarket.dto.wrapper.ErrorCode;
import com.munchymarket.MunchyMarket.security.CustomMemberDetails;
import com.munchymarket.MunchyMarket.utils.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractToken(request);

            // JWT가 있는 경우
            if (token != null) {
                // 토큰 만료 여부 확인
                if (jwtUtil.isExpired(token)) {
                    throw new ExpiredJwtException(null, null, "JWT 토큰이 만료되었습니다.");
                }

                // 인증 설정
                setAuthentication(token);
            }

            // 필터 체인 계속 실행
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException ex) {
            handleExpiredJwtException(request, response, ex);
        }
//        catch (Exception ex) {
//            handleOtherExceptions(request, response, ex);
//        }
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


    private void handleExpiredJwtException(HttpServletRequest request, HttpServletResponse response, ExpiredJwtException ex) throws IOException {
        log.error("Expired JWT token: {}", ex.getMessage());

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        String jsonResponse = objectMapper.writeValueAsString(
                ApiResponse.ofFail(ErrorCode.TOKEN_EXPIRED, ErrorCode.DetailMessage.TOKEN_EXPIRED)
        );
        response.getWriter().write(jsonResponse);
    }

//    private void handleOtherExceptions(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
//        log.error("An error occurred: {}", ex.getMessage());
//
//        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write("{\"error\": \"서버에서 문제가 발생했습니다.\"}");
//    }


    private void setAuthentication(String token) {
        String email = jwtUtil.getLoginId(token);
        String role = jwtUtil.getRole(token);
        Long id = jwtUtil.getId(token);

        Member member = new Member(id, email, role);
        CustomMemberDetails customMemberDetails = new CustomMemberDetails(member);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

}

