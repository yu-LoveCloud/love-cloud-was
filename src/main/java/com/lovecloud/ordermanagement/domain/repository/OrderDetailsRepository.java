package com.lovecloud.ordermanagement.domain.repository;

import com.lovecloud.ordermanagement.domain.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {


    List<OrderDetails> findAllByOrderId(Long id);

    @Query("SELECT od.funding.id FROM OrderDetails od WHERE od.funding.id IN :fundingIds AND od.order.orderStatus != 'ORDER_PLACED'")
    List<Long> findDuplicatedFundingIds(@Param("fundingIds") List<Long> fundingIds);


}
