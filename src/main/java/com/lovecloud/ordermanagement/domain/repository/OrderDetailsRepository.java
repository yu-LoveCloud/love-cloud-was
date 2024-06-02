package com.lovecloud.ordermanagement.domain.repository;

import com.lovecloud.ordermanagement.domain.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Long> {
}
