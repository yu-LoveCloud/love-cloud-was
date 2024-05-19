package com.lovecloud.invitationmanagement.application;

import com.lovecloud.invitationmanagement.application.command.CreateInvitationCommand;
import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.domain.InvitationImage;
import com.lovecloud.invitationmanagement.domain.repository.InvitationImageRepository;
import com.lovecloud.invitationmanagement.domain.repository.InvitationRepository;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InvitationCreateService {
    private final InvitationRepository invitationRepository;
    private final InvitationImageRepository invitationImageRepository;
    private final CoupleRepository coupleRepository;

    public Long addInvitation(final CreateInvitationCommand command) {

        Couple couple = getCoupleByUserId(command.userId());

        InvitationImage invitationImage = invitationImageRepository.findByIdOrThrow(command.invitationImageId());
        Invitation invitation = command.toInvitation(invitationImage);

        Invitation savedInvitation = invitationRepository.save(invitation);

        couple.setInvitation(savedInvitation);

        return savedInvitation.getId();
    }

    private Couple getCoupleByUserId(Long userId) {
        return coupleRepository.findByUserIdOrThrow(userId);

    }


}
