package com.lovecloud.fundingmanagement.domain.repository;

import com.lovecloud.fundingmanagement.domain.Funding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FundingRepository extends JpaRepository<Funding, Long> {

}
