package com.lovecloud.global.crypto;

import org.springframework.stereotype.Component;

@Component
public class BCryptCustomPasswordEncoder implements CustomPasswordEncoder{

    private final BCryptCustomPasswordEncoder passwordEncoder;


    public BCryptCustomPasswordEncoder() {
        this.passwordEncoder = new BCryptCustomPasswordEncoder();
    }

    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
