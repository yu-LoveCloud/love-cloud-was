package com.lovecloud.ordermanagement.domain.repository;

import com.lovecloud.ordermanagement.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
