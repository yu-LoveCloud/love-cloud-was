package com.lovecloud.usermanagement.application;

import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.domain.repository.InvitationRepository;
import com.lovecloud.usermanagement.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CoupleService {
    private final CoupleRepository coupleRepository;
    private final InvitationRepository invitationRepository;
    public void updateCoupleInvitation(Long userId, Long invitationId){
        Invitation invitation = invitationRepository.findByIdOrThrow(invitationId);
        coupleRepository.findByUserIdOrThrow(userId).setInvitation(invitation);
    }
}
