package com.lovecloud.invitationmanagement.application;

import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.domain.repository.InvitationRepository;
import com.lovecloud.invitationmanagement.query.response.InvitationDetailResponse;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InvitationQueryService {

    private final InvitationRepository invitationRepository;
    private final CoupleRepository coupleRepository;
    public InvitationDetailResponse findById(Long invitationId) {

        Invitation invitation = invitationRepository.findByIdOrThrow(invitationId);
        Couple couple = coupleRepository.findByInvitationIdOrThrow(invitationId);
        return InvitationDetailResponse.from(invitation, couple);
    }
}
