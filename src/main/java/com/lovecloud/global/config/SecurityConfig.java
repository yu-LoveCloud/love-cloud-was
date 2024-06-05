package com.lovecloud.global.config;

import com.lovecloud.global.crypto.BCryptCustomPasswordEncoder;
import com.lovecloud.global.crypto.CustomPasswordEncoder;
import com.lovecloud.global.jwt.handler.JwtAccessDeniedHandler;
import com.lovecloud.global.jwt.JwtAuthenticationFilter;
import com.lovecloud.global.jwt.JwtAuthenticationProvider;
import com.lovecloud.global.jwt.JwtTokenProvider;
import com.lovecloud.global.jwt.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    private static final String[] PUBLIC_ENDPOINTS = {
            "/auth/wedding-user/sign-up",
            "/auth/wedding-user/sign-in",
            "/auth/guest/sign-up",
            "/auth/guest/sign-in",

    };


    @Bean
    public CustomPasswordEncoder passwordEncoder() {
        return new BCryptCustomPasswordEncoder();
    }


    @Bean
    WebSecurityCustomizer webSecurityCustomizer() { //정적 리소스 무시
        return (web) -> web.ignoring().requestMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        configureHttpSecurity(httpSecurity);
        return httpSecurity.build();
    }

    private void configureHttpSecurity(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)

                .cors(withDefaults())

                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_ENDPOINTS).permitAll()
                        .anyRequest().authenticated())


                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(jwtAccessDeniedHandler) //권한 문제 발생
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)) //인증 문제 발생


//                .authorizeHttpRequests(auth -> auth
//                        .anyRequest().permitAll())  // 모든 요청을  허용

                .addFilterBefore(jwtAuthenticationFilter(authenticationManager(httpSecurity)),
                    UsernamePasswordAuthenticationFilter.class);



    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(jwtAuthenticationProvider)
                .build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new JwtAuthenticationFilter(jwtTokenProvider, authenticationManager);
    }

}
