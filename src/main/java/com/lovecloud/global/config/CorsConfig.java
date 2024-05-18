package com.lovecloud.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {


    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(true); // 인증이 필요한 요청에서만 쿠키 허용
        config.addAllowedOrigin("*"); // 허용할 출처
        config.addAllowedHeader("*"); // 허용할 헤더
        config.addAllowedMethod("*"); // 허용할 메소드

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
