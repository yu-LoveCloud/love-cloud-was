package com.lovecloud.ordermanagement.domain.repository;

import com.lovecloud.ordermanagement.domain.DeliveryAddress;
import com.lovecloud.ordermanagement.exception.DeliveryAddressNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

    default DeliveryAddress findByIdOrThrow(Long id){
        return findById(id).orElseThrow(DeliveryAddressNotFoundException::new);
    }

    List<DeliveryAddress> findAllByCoupleId(Long coupleId);

    Optional<DeliveryAddress> findByIdAndCoupleId(Long deliveryAddressId, Long coupleId);

    default DeliveryAddress findByIdAndCoupleIdOrThrow(Long deliveryAddressId, Long coupleId){
        return findByIdAndCoupleId(deliveryAddressId, coupleId).orElseThrow(DeliveryAddressNotFoundException::new);
    }

    @Query("select da from DeliveryAddress da where da.couple.id = :coupleId and da.isDefault = true")
    Optional<DeliveryAddress> findDefaultAddressByCoupleId(Long coupleId);

    default DeliveryAddress findDefaultAddressByCoupleIdOrThrow(Long id) {
        return findDefaultAddressByCoupleId(id).orElseThrow(DeliveryAddressNotFoundException::new);
    }
}
