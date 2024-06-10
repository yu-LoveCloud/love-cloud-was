package com.lovecloud.usermanagement.application;

import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.WeddingRole;
import com.lovecloud.usermanagement.domain.WeddingUser;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import com.lovecloud.usermanagement.domain.repository.WeddingUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CoupleCreationService {

    private WeddingUserRepository weddingUserRepository;
    private CoupleRepository coupleRepository;

    public void createCouple(WeddingUser newUser, String invitationCode) {

        WeddingUser existingUser = weddingUserRepository.findByInvitationCodeOrThrow(invitationCode);

        Couple couple = Couple.builder()
                .groom(newUser.getWeddingRole() == WeddingRole.GROOM ? newUser : existingUser)
                .bride(newUser.getWeddingRole() == WeddingRole.BRIDE ? newUser : existingUser)
                .build();

        coupleRepository.save(couple);
    }
}
