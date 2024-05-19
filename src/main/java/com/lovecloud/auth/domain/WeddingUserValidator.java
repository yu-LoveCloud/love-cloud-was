package com.lovecloud.auth.domain;

import com.lovecloud.auth.exception.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeddingUserValidator {

    private final WeddingUserRepository weddingUserRepository;

    public void validateDuplicateEmail(String email) {
        if (weddingUserRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
