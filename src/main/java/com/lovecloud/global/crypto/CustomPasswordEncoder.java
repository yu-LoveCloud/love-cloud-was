package com.lovecloud.global.crypto;

import com.lovecloud.auth.domain.Password;

public interface CustomPasswordEncoder {
    Password encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
