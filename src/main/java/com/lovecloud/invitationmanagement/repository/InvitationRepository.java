package com.lovecloud.invitationmanagement.repository;

import com.lovecloud.invitationmanagement.domain.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
