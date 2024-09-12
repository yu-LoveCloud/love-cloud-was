package com.lovecloud.ordermanagement.domain.repository;

import com.lovecloud.ordermanagement.domain.DeliveryAddress;
import com.lovecloud.ordermanagement.exception.DeliveryAddressNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

    default DeliveryAddress findByIdOrThrow(Long id){
        return findById(id).orElseThrow(DeliveryAddressNotFoundException::new);
    }
}
