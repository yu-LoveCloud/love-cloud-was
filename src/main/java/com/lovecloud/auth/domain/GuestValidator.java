package com.lovecloud.auth.domain;

import com.lovecloud.auth.exception.DuplicateEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GuestValidator {

    private final GuestRepository guestRepository;

    public void validateDuplicateEmail(String email) {
        if (guestRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
