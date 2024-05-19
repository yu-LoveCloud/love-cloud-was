package com.lovecloud.global.crypto;

public interface CustomPasswordEncoder {
    String encode(CharSequence rawPassword);
    boolean matches(CharSequence rawPassword, String encodedPassword);
}
