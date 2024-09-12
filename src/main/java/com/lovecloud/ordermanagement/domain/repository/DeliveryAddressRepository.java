package com.lovecloud.ordermanagement.domain.repository;

import com.lovecloud.ordermanagement.domain.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {
}
