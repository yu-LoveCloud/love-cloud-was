package com.lovecloud.fundingmanagement.domain.repository;

import com.lovecloud.fundingmanagement.domain.GuestFunding;
import com.lovecloud.fundingmanagement.domain.ParticipationStatus;
import com.lovecloud.fundingmanagement.exception.NotFoundGuestFundingException;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestFundingRepository extends JpaRepository<GuestFunding, Long> {

    List<GuestFunding> findByFundingIdAndParticipationStatus(Long fundingId,
            ParticipationStatus participationStatus);

    default GuestFunding findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(NotFoundGuestFundingException::new);
    }
}
