package com.lovecloud.usermanagement.domain.repository;

import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.WeddingUser;
import com.lovecloud.usermanagement.exception.NotFoundCoupleException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CoupleRepository extends JpaRepository<Couple, Long> {

    @Query("SELECT c FROM Couple c WHERE c.groom.id = :memberId OR c.bride.id = :memberId")
    Optional<Couple> findByMemberId(@Param("memberId") Long memberId);

    default Couple findByMemberIdOrThrow(Long memberId) {
        return findByMemberId(memberId).orElseThrow(NotFoundCoupleException::new);
    }

    Optional<Couple> findByInvitationId(Long invitationId);

    default Couple findByInvitationIdOrThrow(Long invitationId) {
        return findByInvitationId(invitationId).orElseThrow(NotFoundCoupleException::new);
    }

    default Couple findByIdOrThrow(Long coupleId) {
        return findById(coupleId).orElseThrow(NotFoundCoupleException::new);
    }

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Couple c WHERE c.groom = :user OR c.bride = :user")
    boolean existsByGroomOrBride(@Param("user") WeddingUser user);
}

