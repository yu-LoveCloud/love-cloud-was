package com.lovecloud.invitationmanagement.application;

import com.lovecloud.invitationmanagement.application.command.CreateInvitationCommand;
import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.domain.InvitationImage;
import com.lovecloud.invitationmanagement.domain.repository.InvitationImageRepository;
import com.lovecloud.invitationmanagement.domain.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InvitationCreateService {
    private final InvitationRepository invitationRepository;
    private final InvitationImageRepository invitationImageRepository;

    public Long addInvitation(final CreateInvitationCommand command) {

        InvitationImage invitationImage = invitationImageRepository.findByIdOrThrow(command.invitationImageId());
        Invitation invitation = command.toInvitation(invitationImage);

        Invitation savedInvitation = invitationRepository.save(invitation);

        return savedInvitation.getId();
    }

}
