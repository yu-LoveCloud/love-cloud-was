package com.lovecloud.auth.domain;

import com.lovecloud.auth.exception.NotMatchPasswordException;
import com.lovecloud.global.crypto.CustomPasswordEncoder;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class Password {

    private String encryptedPassword;

    public Password(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public void validatePassword(String rawPassword, CustomPasswordEncoder encoder) {
        if (!encoder.matches(rawPassword, encryptedPassword)) {
            throw new NotMatchPasswordException();
        }
    }
}