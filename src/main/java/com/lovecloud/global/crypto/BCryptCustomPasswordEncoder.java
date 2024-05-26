package com.lovecloud.global.crypto;

import com.lovecloud.auth.domain.Password;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BCryptCustomPasswordEncoder implements CustomPasswordEncoder{

    private final BCryptPasswordEncoder passwordEncoder;


    public BCryptCustomPasswordEncoder() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Password encode(CharSequence rawPassword)  {
        String encoded = passwordEncoder.encode(rawPassword);
        return new Password(encoded);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
