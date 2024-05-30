package com.lovecloud.fundingmanagement.domain.repository;

import com.lovecloud.fundingmanagement.domain.Funding;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingRepository extends JpaRepository<Funding, Long> {

    List<Funding> findByCoupleId(Long coupleId);
}
