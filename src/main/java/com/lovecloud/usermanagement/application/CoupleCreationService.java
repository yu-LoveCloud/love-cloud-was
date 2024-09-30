package com.lovecloud.usermanagement.application;

import com.lovecloud.auth.domain.WeddingUserRepository;
import com.lovecloud.auth.domain.WeddingUserValidator;
import com.lovecloud.blockchain.application.WalletCreationService;
import com.lovecloud.blockchain.domain.Wallet;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.WeddingRole;
import com.lovecloud.usermanagement.domain.WeddingUser;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CoupleCreationService {

    private final WeddingUserRepository weddingUserRepository;
    private final WeddingUserValidator weddingUserValidator;
    private final CoupleRepository coupleRepository;
    private final WalletCreationService walletCreationService;

    public void createCouple(WeddingUser newUser, String invitationCode) {

        WeddingUser existingUser = weddingUserRepository.findByInvitationCodeOrThrow(invitationCode);

        weddingUserValidator.validateDuplicatePartner(existingUser);
        weddingUserValidator.validateDuplicatePartner(newUser);
        weddingUserValidator.validateDuplicateGender(newUser, existingUser);

        Couple couple = Couple.builder()
                .groom(newUser.getWeddingRole() == WeddingRole.GROOM ? newUser : existingUser)
                .bride(newUser.getWeddingRole() == WeddingRole.BRIDE ? newUser : existingUser)
                .build();

        coupleRepository.save(couple);

        Wallet wallet = walletCreationService.saveWallet();
        couple.assignWallet(wallet);

        coupleRepository.save(couple);
    }
}
