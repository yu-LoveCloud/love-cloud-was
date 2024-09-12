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

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryAddressService {
    private final DeliveryAddressRepository deliveryAddressRepository;
    private final CoupleRepository coupleRepository;

    public Long createDeliveryAddress(CreateDeliveryAddressCommand command) {
        Couple couple = coupleRepository.findByMemberIdOrThrow(command.userId());
        DeliveryAddress deliveryAddress = command.toDeliveryAddress(couple);

        DeliveryAddress savedDeliveryAddress = deliveryAddressRepository.save(deliveryAddress);
        if (command.isDefault()) {
            setDefaultDeliveryAddress(command.userId(), savedDeliveryAddress.getId());
        }
        return savedDeliveryAddress.getId();
    }


    public Long updateDeliveryAddress(UpdateDeliveryAddressCommand command) {
        Couple couple = coupleRepository.findByMemberIdOrThrow(command.userId());
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByIdOrThrow(command.deliveryAddressId());

        //본인의 배송지인지 검증
        validateOwner(deliveryAddress, couple);

        deliveryAddress.update(command);
        if(command.isDefault()){
            setDefaultDeliveryAddress(command.userId(), command.deliveryAddressId());
        }

        return deliveryAddress.getId();
    }

    public void deleteDeliveryAddress(Long userId, Long deliveryAddressId) {
        Couple couple = coupleRepository.findByMemberIdOrThrow(userId);
        DeliveryAddress deliveryAddress = deliveryAddressRepository.findByIdOrThrow(deliveryAddressId);

        //본인의 배송지인지 검증
        validateOwner(deliveryAddress, couple);

        deliveryAddressRepository.delete(deliveryAddress);
    }

    private void setDefaultDeliveryAddress(Long userId, Long deliveryAddressId) {

        // 1. 해당 사용자의 모든 배송지를 가져옴
        List<DeliveryAddress> userAddresses = deliveryAddressRepository.findAllByUserId(userId);

        // 2. 새로운 기본 배송지를 찾아 기본으로 설정하고, 나머지는 일반 배송지로 설정
        for (DeliveryAddress address : userAddresses) {
            if (address.getId().equals(deliveryAddressId)) {
                address.setDefault(true);  // 새로운 기본 배송지로 설정
            } else {
                address.setDefault(false);  // 나머지는 일반 배송지로 설정
            }
        }

        // 3. 변경된 배송지들을 저장
        deliveryAddressRepository.saveAll(userAddresses);

    }

    private static void validateOwner(DeliveryAddress deliveryAddress, Couple couple) {
        if (!deliveryAddress.getCouple().getId().equals(couple.getId())) {
            throw new UnauthorizedDeliveryAddressException();
        }
    }
}
