package com.lovecloud.ordermanagement.application;

import com.lovecloud.ordermanagement.application.command.CreateDeliveryAddressCommand;
import com.lovecloud.ordermanagement.domain.DeliveryAddress;
import com.lovecloud.ordermanagement.domain.repository.DeliveryAddressRepository;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final CoupleRepository coupleRepository;

    public Long createDeliveryAddress(CreateDeliveryAddressCommand command) {
        Couple couple = coupleRepository.findByMemberIdOrThrow(command.userId());
        DeliveryAddress deliveryAddress = command.toDeliveryAddress(couple);
        return deliveryAddressRepository.save(deliveryAddress).getId();
    }
}
