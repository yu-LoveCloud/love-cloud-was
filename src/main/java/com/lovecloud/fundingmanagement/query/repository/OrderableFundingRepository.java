package com.lovecloud.fundingmanagement.query.repository;

import com.lovecloud.fundingmanagement.domain.Funding;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 주문 가능한 펀딩을 조회하는 리포지토리입니다. 이 리포지토리는 특정 부부의 완료된 펀딩 중에서 아직 주문되지 않았거나 주문이 취소된 펀딩을 조회하는 기능을 제공합니다.
 */
@Repository
public interface OrderableFundingRepository extends JpaRepository<Funding, Long> {

    /**
     * 특정 부부의 완료된 펀딩 중 주문 가능한 펀딩 목록을 조회합니다. 주문 가능한 펀딩은 펀딩 상태가 'COMPLETED'이며, 관련된 주문이 없거나 주문 상태가
     * 'CANCEL_COMPLETED'인 펀딩을 의미합니다.
     *
     * @param coupleId 부부의 ID
     * @return 특정 부부의 주문 가능한 펀딩 목록을 반환합니다.
     */
    @Query("SELECT f FROM Funding f " +
            "LEFT JOIN OrderDetails od ON f.id = od.funding.id " +
            "LEFT JOIN Order o ON od.order.id = o.id " +
            "WHERE f.couple.id = :coupleId AND f.status = 'COMPLETED' AND " +
            "(o IS NULL OR o.orderStatus = 'CANCEL_COMPLETED')")
    List<Funding> findOrderableFundingsByCoupleId(@Param("coupleId") Long coupleId);
}
