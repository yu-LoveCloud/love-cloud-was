package com.lovecloud.global.config;

import com.lovecloud.global.crypto.BCryptCustomPasswordEncoder;
import com.lovecloud.global.crypto.CustomPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SecurityConfig {

    @Bean
    public CustomPasswordEncoder passwordEncoder() {
        return new BCryptCustomPasswordEncoder();
    }
}
