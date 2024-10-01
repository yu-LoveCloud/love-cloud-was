package com.lovecloud.fundingmanagement.domain.repository;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.GuestFunding;
import com.lovecloud.fundingmanagement.domain.ParticipationStatus;
import com.lovecloud.fundingmanagement.exception.NotFoundGuestFundingException;
import java.util.List;

import com.lovecloud.usermanagement.domain.Guest;
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

    /**
     * 특정 게스트와 펀딩에 대해 PENDING 또는 PAID 상태인 GuestFunding이 있는지 확인합니다.
     *
     * @param guest 검증할 게스트
     * @param funding 검증할 펀딩
     * @return 해당 게스트와 펀딩에 대해 PENDING 또는 PAID 상태의 GuestFunding이 있으면 true, 그렇지 않으면 false
     */
    @Query("SELECT COUNT(gf) > 0 FROM GuestFunding gf " +
            "WHERE gf.guest = :guest AND gf.funding = :funding " +
            "AND gf.participationStatus IN (:statuses)")
    boolean existsByGuestAndFundingWithStatuses(@Param("guest") Guest guest,
                                                @Param("funding") Funding funding,
                                                @Param("statuses") List<ParticipationStatus> statuses);
}
