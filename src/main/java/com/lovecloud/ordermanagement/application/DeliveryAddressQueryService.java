package com.lovecloud.ordermanagement.application;

import com.lovecloud.ordermanagement.domain.DeliveryAddress;
import com.lovecloud.ordermanagement.domain.repository.DeliveryAddressRepository;
import com.lovecloud.ordermanagement.query.response.DeliveryAddressResponse;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryAddressQueryService {
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final CoupleRepository coupleRepository;
    public List<DeliveryAddressResponse> findAllDeliveryAddressesByUserId(Long userId) {
        Couple couple = coupleRepository.findByMemberIdOrThrow(userId);
        List<DeliveryAddress> deliveryAddresses = deliveryAddressRepository.findAllByCoupleId(couple.getId());
        return deliveryAddresses.stream()
                .map(DeliveryAddressResponse::from)
                .sorted(Comparator.comparing(DeliveryAddressResponse::isDefault).reversed())
                .toList();
    }


    public DeliveryAddressResponse findDeliveryAddressByDeliveryAddressIdAndUserId(Long userId, Long deliveryAddressId) {
        Couple couple = coupleRepository.findByMemberIdOrThrow(userId);
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByIdAndCoupleIdOrThrow(deliveryAddressId,couple.getId());
        return DeliveryAddressResponse.from(deliveryAddress);
    }

    public DeliveryAddressResponse findDefaultDeliveryAddressByUserId(Long id) {
        Couple couple = coupleRepository.findByMemberIdOrThrow(id);
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findDefaultAddressByCoupleIdOrThrow(couple.getId());
        return DeliveryAddressResponse.from(deliveryAddress);
    }
}
