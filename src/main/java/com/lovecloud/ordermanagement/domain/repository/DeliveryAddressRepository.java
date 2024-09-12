package com.lovecloud.ordermanagement.domain.repository;

import com.lovecloud.ordermanagement.domain.DeliveryAddress;
import com.lovecloud.ordermanagement.exception.DeliveryAddressNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

    default DeliveryAddress findByIdOrThrow(Long id){
        return findById(id).orElseThrow(DeliveryAddressNotFoundException::new);
    }

    List<DeliveryAddress> findAllByCoupleId(Long coupleId);
}
