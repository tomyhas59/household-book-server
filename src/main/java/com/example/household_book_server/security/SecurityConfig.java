package com.example.household_book_server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource()) // CORS 설정 추가
                )
                .csrf(AbstractHttpConfigurer::disable // CSRF 보호 비활성화
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/signup", "/login", "/**").permitAll() // 허용된 경로
                        .requestMatchers("/").authenticated() // 메인 페이지는 인증 필요
                        .anyRequest().authenticated() // 나머지 요청은 인증 필요
                )

                .formLogin(form -> form
                        .loginPage("/login") // 로그인 페이지 경로
                        .loginProcessingUrl("/login") // 로그인 처리 엔드포인트, POST 요청 처리
                        .defaultSuccessUrl("/", true) // 로그인 성공 후 리디렉션할 URL
                        .permitAll()
                );

        return http.build();
    }

    // CORS 설정을 정의하는 Bean
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // React 앱의 도메인
        configuration.setAllowedMethods(Arrays.asList("POST","GET", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 인코딩
    }
}
