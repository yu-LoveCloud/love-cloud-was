package com.lovecloud.usermanagement.domain.repository;

import com.lovecloud.auth.exception.NotFoundWeddingUserException;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.WeddingUser;
import com.lovecloud.usermanagement.exception.NotFoundCoupleException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface WeddingUserRepository extends JpaRepository<WeddingUser, Long> {

    Optional<WeddingUser> findByInvitationCode(String invitationCode);

    default WeddingUser findByInvitationCodeOrThrow(String invitationCode) {
        return findByInvitationCode(invitationCode).orElseThrow(NotFoundWeddingUserException::new);
    }
}
