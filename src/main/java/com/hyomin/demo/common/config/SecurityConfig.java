package com.hyomin.demo.common.config;

import com.hyomin.demo.common.auth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화
            .cors(cors -> cors.disable()) // 기본 CORS 정책 비활성화, 필요 시 적절히 설정
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/private/**").authenticated() // private으로 시작하는 URI는 로그인 필수
                .anyRequest().permitAll() // 나머지 URI는 모든 접근 허용
            )
            .oauth2Login(oauth2Login -> oauth2Login
                .loginPage("/loginForm") // 로그인이 필요한데 로그인을 하지 않았다면 이동할 URI 설정
                .defaultSuccessUrl("/") // OAuth 구글 로그인이 성공하면 이동할 URI 설정
                .userInfoEndpoint(userInfo -> userInfo
                    .userService(customOAuth2UserService) // 로그인 후 받아온 유저 정보
                )
            );

        return http.build();
    }
}
