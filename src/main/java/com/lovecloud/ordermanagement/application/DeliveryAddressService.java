package com.lovecloud.ordermanagement.application;

import com.lovecloud.ordermanagement.application.command.CreateDeliveryAddressCommand;
import com.lovecloud.ordermanagement.application.command.UpdateDeliveryAddressCommand;
import com.lovecloud.ordermanagement.domain.DeliveryAddress;
import com.lovecloud.ordermanagement.domain.repository.DeliveryAddressRepository;
import com.lovecloud.ordermanagement.exception.UnauthorizedDeliveryAddressException;
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

    public Long updateDeliveryAddress(UpdateDeliveryAddressCommand command) {
        Couple couple = coupleRepository.findByMemberIdOrThrow(command.userId());
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByIdOrThrow(command.deliveryAddressId());

        //본인의 배송지인지 검증
        validateOwner(deliveryAddress, couple);

        deliveryAddress.update(command);
        return deliveryAddress.getId();
    }

    private static void validateOwner(DeliveryAddress deliveryAddress, Couple couple) {
        if (!deliveryAddress.getCouple().getId().equals(couple.getId())) {
            throw new UnauthorizedDeliveryAddressException();
        }
    }
}
