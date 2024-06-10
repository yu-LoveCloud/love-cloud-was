package com.lovecloud.fundingmanagement.domain.repository;

import com.lovecloud.fundingmanagement.domain.GuestFunding;
import com.lovecloud.fundingmanagement.domain.ParticipationStatus;
import com.lovecloud.fundingmanagement.exception.NotFoundGuestFundingException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestFundingRepository extends JpaRepository<GuestFunding, Long> {

    List<GuestFunding> findByFundingIdAndParticipationStatus(Long fundingId,
            ParticipationStatus participationStatus);

    @Query("SELECT COUNT(gf) FROM GuestFunding gf WHERE gf.funding.id = :fundingId AND gf.participationStatus = :status")
    int countByFundingIdAndParticipationStatus(
            @Param("fundingId") Long fundingId,
            @Param("status") ParticipationStatus status
    );

    default GuestFunding findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(NotFoundGuestFundingException::new);
    }
}
