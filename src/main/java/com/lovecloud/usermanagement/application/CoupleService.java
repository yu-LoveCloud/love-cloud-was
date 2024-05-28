package com.lovecloud.usermanagement.application;

import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.domain.repository.InvitationRepository;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CoupleService {
    private final CoupleRepository coupleRepository;
    private final InvitationRepository invitationRepository;
    public void updateCoupleInvitation(Long memberId, Long invitationId){
        Invitation invitation = invitationRepository.findByIdOrThrow(invitationId);
        coupleRepository.findByMemberIdOrThrow(memberId).setInvitation(invitation);
    }
}
