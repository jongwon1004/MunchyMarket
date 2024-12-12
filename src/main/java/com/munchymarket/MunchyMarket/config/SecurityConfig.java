package com.munchymarket.MunchyMarket.config;


import com.munchymarket.MunchyMarket.filter.JWTFilter;
import com.munchymarket.MunchyMarket.filter.LoginFilter;
import com.munchymarket.MunchyMarket.handler.CustomAccessDeniedHandler;
import com.munchymarket.MunchyMarket.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;


    // AuthenticationManagerのBean登録
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }


    // 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure() { // 스프링 시큐리티의 모든 기능 (인증, 인가) 를 사용하지 않게 설정
        return (web) -> web.ignoring()  // requestMatchers() 에 적힌 url 에 대해 인증, 인가 서비스를 적용하지 않음
                .requestMatchers("/static/**"); // 특정 요청과 일치하는  url 에 대한 액세스 설정
    }

    /**
     * 特定HTTP RequestについてのWEB基盤セキュリティ構成
     * 認証・認可、ログイン、ログアウト設定
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, CustomAccessDeniedHandler customAccessDeniedHandler) throws Exception {

        /**
         * SpringSecurity는 디폴트값으로 /login 로 URL 요청을 받는다. 타임리프나 SSR 을 사용하는 경우에는 .formLogin 설정에서 .loginProcessingUrl() 을 사용하여 경로를 바꿔줄 수 있지만
         * React 나 Vue 를 사용하는 경우에는 .formLogin 설정에서 .loginProcessingUrl() 을 사용하여 경로를 바꿔줄 수 없다. 이럴 경우에는 .formLogin 설정을 사용하지 않고
         * 밑에처럼 LoginFilter 에 .setFilterProcessesUrl() 을 사용하여 경로를 바꿔줄 수 있다.
         * setFilterProcessesUrl() 로 login 경로를 수정하고 이 필터를 밑에 .addFilterAt() 메서드에서 사용하면 된다!!!
         */
        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        loginFilter.setFilterProcessesUrl("/api/member/login");

        return http
                .authorizeRequests(authorize -> authorize
                        .requestMatchers("/api/member/", "/api/member/login",
                                "/api/member/send-sms", "/api/member/validate", "/api/member/verification-code", "/api/member/join",
                                "/api/admin/product/register", "/api/admin/product/categories", "/api/admin/product/packaging-types", //テスト段階なので、一時的に許可
                                "/api/categories/**","/api/products/**",
                                "/api/payment/**","/api/webhooks/stripe",
                                "/favicon.ico", "/robots.txt", "/sitemap.xml",
                                "/logout", "/error", "/swagger-ui/**", "/api-docs/**", "/test/**", "/v3/api-docs", "/api/async/**").permitAll() // 誰でもアクセス可能。requestMatchers() に記載されたURLは認証、認可がなくてもアクセス可能
                        .requestMatchers("/api/member/role-check").hasRole("ADMIN") // ADMIN　権限を持つユーザーだけアクセス可能
                        .requestMatchers("/profile", "/api/accounts/register/", "/api/accounts/**",
                                           "/api/review", "/api/order/create").authenticated() // 認証済みのユーザーだけアクセス可能
                        .anyRequest().authenticated() // それ以外のリクエストは認証が必要 // 403 Forbidden
                )
                .formLogin(form -> form // ログインページををクライアント側で管理する場合は、設定不要
//                                .usernameParameter("email")
//                                .passwordParameter("password")
//                                .loginProcessingUrl("/login") // ログイン処理URL、このpathにPOST Requestが来ると、Spring Securityがログイン処理を試みる
//                                .permitAll()

//                        .loginPage("/login") // ログインページ設定。　設定しない場合、デフォルトのログインページが表示される
//                        .defaultSuccessUrl("/") // ログイン成功時のリダイレクト先
//                        .permitAll()
                                .disable()
                )
                .httpBasic(auth -> auth.disable()) // HTTP基本認証はパスワードを暗号化せずに送信するため、セキュリティ上のリスクがあるため、使用しない -> JWT使用
//                .logout(logout -> logout // LogoutControllerでカスタムログアウト処理を行う場合は、設定不要
//                        .logoutUrl("/member/logout") // ログアウトRequestURL
//                        .logoutSuccessUrl("/") // ログアウト成功時のリダイレクト先。　
//                        .invalidateHttpSession(true) // ログアウト時にセッションを無効化するかどうか
//                        .permitAll()
//                )
                // JWTFilter登録
                // JWTFilterをLoginFilterの前に追加する設定。つまりLoginFilterが実行される前にJWTFilterが先に実行される
                // これは、JWTTokenがあるRequestを先に処理し、認証されてるRequestかどうかを確認するため
                .addFilterBefore(new JWTFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class)

                // Filter追加、LoginFilter()引数をもらう
                // (AuthenticationManager() methodに　authenticationConfiguration()オブジェクトを追加しないといけない) -> 登録必要
                // SecurityConfig : AuthenticationManagerのBean登録していないため、AuthenticationManagerを取得できない -> 追加
                // addFilterAtで既存のUsernamePasswordAuthenticationFilterの代わりにLoginFilterが動作する
                // ただし、ControllerでUsernamePasswordAuthenticationFilterを直接使用しているため、不要
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(csrf -> csrf
                        .disable()
                )
                // 403 Forbidden時の処理をカスタマイズ
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(customAccessDeniedHandler)
                )
                .cors(withDefaults())
//                .oauth2Login(Customizer.withDefaults())
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 생성 정책 설정. STATELESS : 세션을 사용하지 않음
                )
                .build();
    }


    // パスワード暗号化のためのBean登録
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * SpringBoot 에서 CORS 설정을 하려고했는데 해결이 안됨
     * Apache 서버 (/etc/apache2/sites-available/192.***.conf) 에서 CORS 설정을 하고 Response Header 확인하면 Origin 이 설정되어있는걸 확인했음
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
//      명시적으로 허용할 Origin 지정
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:5173",
                "http://localhost:5500",
                "http://127.0.0.1:5500"
        ));

        // 허용할 HTTP 메서드 지정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // 허용할 헤더 지정
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With"));

        // 노출할 헤더 지정
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        // 쿠키 및 인증 정보 허용
        configuration.setAllowCredentials(true);

        // URL 기반으로 CORS 설정 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    //    // 인증 관리자 관련 설정, 사용자 정보를 가져올 서비스를 재정의 하거나, 인증 방법 등을 설정
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//
//        daoAuthenticationProvider.setUserDetailsService(userService);  // 사용자 정보를 가져올 서비스를 설정. 이때 설정하는 클래스는 반드시 UserDetailsService 를 상속받은 클래스이여야 함.
//        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder()); // 비밀번호 암호화를 위한 인코더 설정
//
//        return daoAuthenticationProvider;
//    }


}

