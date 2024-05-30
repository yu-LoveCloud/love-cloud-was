package com.lovecloud.invitationmanagement.domain.repository;

import com.lovecloud.invitationmanagement.domain.Invitation;
import com.lovecloud.invitationmanagement.exeption.NotFoundInvitationException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
    default Invitation findByIdOrThrow(Long invitationId){
        return findById(invitationId).orElseThrow(NotFoundInvitationException::new);
    }
}
