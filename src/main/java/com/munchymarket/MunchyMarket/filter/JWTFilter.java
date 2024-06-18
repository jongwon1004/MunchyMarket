package com.munchymarket.MunchyMarket.filter;

import com.munchymarket.MunchyMarket.domain.Member;
import com.munchymarket.MunchyMarket.security.CustomMemberDetails;
import com.munchymarket.MunchyMarket.utils.JWTUtil;
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

@RequiredArgsConstructor
@Slf4j
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String loginId = request.getParameter("loginId");
        log.info("loginId = {}", loginId);
        log.info("request.getRequestURI() = {}", request.getRequestURI());
        // 로그인 경로에 대한 요청은 JWT 검증을 건너뛴다.
        if (request.getRequestURI().equals("/api/member/login")) {
            log.info("JWTFilter에서 JWT검증을 건너뜁니다");
            filterChain.doFilter(request, response);
            return;
        }

        // RequestのHeaderからJWTを取得
        String authorization = request.getHeader("Authorization");
        log.info("authorization = {}", authorization);

        // Authorization Header検証
        if(authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("token is null");
            filterChain.doFilter(request, response);

            // 条件が該当するとメソッド終了（必須）
            return;
        }

        log.info("authorization now");
        log.info("authorization = {}", authorization);

        // Bearerを取り除いてJWTを取得
        String token = authorization.split(" ")[1];

        // Tokenの消滅時間を検証
        if(jwtUtil.isExpired(token)) {
            log.error("token is expired");
            filterChain.doFilter(request, response);

            // 条件が該当するとメソッド終了（必須）
            return;
        }

        // tokenからemail, roleを取得
        String email = jwtUtil.getLoginId(token);
        String role = jwtUtil.getRole(token);
        Long id = jwtUtil.getId(token);
        log.info("id ={}", id);


        // Memberを生成して値をSet
        Member member = new Member(id, email, role);

        // MemberDetailsにMemberの情報を入れる
        CustomMemberDetails customMemberDetails = new CustomMemberDetails(member);

        // Spring Security認証Token生成
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(customMemberDetails, null, customMemberDetails.getAuthorities());

        // Sessionに使用者登録
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}

