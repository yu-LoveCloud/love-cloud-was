package com.lovecloud.ordermanagement.domain.repository;

import com.lovecloud.ordermanagement.domain.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {


    List<OrderDetails> findAllByOrderId(Long id);

    boolean existsByFundingId(Long id);

}
