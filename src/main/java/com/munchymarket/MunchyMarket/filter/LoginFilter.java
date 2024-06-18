package com.munchymarket.MunchyMarket.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.munchymarket.MunchyMarket.security.CustomMemberDetails;
import com.munchymarket.MunchyMarket.utils.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * AuthenticationManagerをInjectionして、これで検証を行う
 * 検証する時は UsernamePasswordAuthenticationTokenというDTOに入れて送る
 * 検証が成功すると、successfulAuthentication()が呼ばれる、失敗するとunsuccessfulAuthentication()が呼ばれる
 * TODO: 成功時、失敗時の処理の実装
 */
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    //    @Value("${spring.jwt.expiration}")
//    private Long expiration;

    @Override
    protected String obtainUsername(HttpServletRequest request) {
        String username = request.getParameter("loginId");
        log.info("Attempting to log in with username: {}", username);
        return username;
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        String password = request.getParameter("password");
        log.info("Attempting to log in with password: {}", password);
        return password;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // クライアントのRequestからusername, passwordを抽出
        String username = obtainUsername(request);// request.getParameter("loginId");
        String password = obtainPassword(request);

        log.info("username = {}", username);
        log.info("password = {}", password);

        // Spring SecurityでUsernameとpasswordを検証するためにはtokenに入れ込まないといけない
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        // tokenの検証のためのAuthenticationManagerに渡す
        return authenticationManager.authenticate(authToken);
    }

    /**
     * ログイン成功時の処理。　（ここでJWTを発行）
     * ログイン成功時　successfulAuthentication() methodを通じてJWTを発行しないといけない。
     * JWT　Responseを作成するけど、JWT発行クラスをまだ作ってないから、DB基盤の検証を行ってからJWTの発行,検証するクラスを生成しないといけない。
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {

        log.info("successfulAuthentication() called!!!");

        CustomMemberDetails customMemberDetails = (CustomMemberDetails) authentication.getPrincipal();

        String loginId = customMemberDetails.getUsername();
        Long id = customMemberDetails.getId();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(id, loginId, role, 60 * 60 * 1000L * 10); // 10時間
        log.info("token = {}", token);

        response.addHeader("Authorization", "Bearer " + token);

        // 요청 속성에 토큰 저장
        request.setAttribute("jwtToken", token);
        request.setAttribute("userDetails", customMemberDetails);

        // SecurityContextHolder에 인증 정보 설정
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 인증 성공 후 필터 체인 계속
        chain.doFilter(request, response);
    }

    /**
     * ログイン失敗時の処理
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "email or password is incorrect");

        // Java 객체를 JSON 문자열로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.getWriter().write(jsonResponse);
    }


}