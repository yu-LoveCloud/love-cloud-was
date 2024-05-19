package com.lovecloud.invitationmanagement.domain.repository;

import com.lovecloud.invitationmanagement.domain.InvitationImage;
import com.lovecloud.invitationmanagement.exeption.NotFoundInvitationImageException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationImageRepository extends JpaRepository<InvitationImage, Long> {

    Optional<InvitationImage> findByImageName(String imageName);

    default InvitationImage findByImageNameOrThrow(String imageName) {
        return findByImageName(imageName)
                .orElseThrow(NotFoundInvitationImageException::new);
    }
}
