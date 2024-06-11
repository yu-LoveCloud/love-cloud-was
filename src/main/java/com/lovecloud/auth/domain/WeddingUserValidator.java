package com.lovecloud.auth.domain;

import com.lovecloud.auth.exception.DuplicateEmailException;
import com.lovecloud.auth.exception.DuplicateGenderException;
import com.lovecloud.auth.exception.DuplicatePartnerException;
import com.lovecloud.usermanagement.domain.WeddingUser;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WeddingUserValidator {

    private final WeddingUserRepository weddingUserRepository;
    private final CoupleRepository coupleRepository;

    public void validateDuplicateEmail(String email) {
        if (weddingUserRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    public void validateDuplicateGender(WeddingUser user1, WeddingUser user2) {
        if (user1.getWeddingRole() == user2.getWeddingRole()) {
            throw new DuplicateGenderException();
        }
    }

    public void validateDuplicatePartner(WeddingUser user) {
        if (coupleRepository.existsByGroomOrBride(user)) {
            throw new DuplicatePartnerException();
        }
    }
}
