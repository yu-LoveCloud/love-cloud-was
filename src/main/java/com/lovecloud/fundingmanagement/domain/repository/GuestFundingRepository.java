package com.lovecloud.fundingmanagement.domain.repository;

import com.lovecloud.fundingmanagement.domain.GuestFunding;
import com.lovecloud.fundingmanagement.exception.NotFoundGuestFundingException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestFundingRepository extends JpaRepository<GuestFunding, Long> {

    default GuestFunding findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(NotFoundGuestFundingException::new);
    }
}
