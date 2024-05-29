package com.lovecloud.ordermanagement.application;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.FundingStatus;
import com.lovecloud.fundingmanagement.domain.repository.FundingRepository;
import com.lovecloud.ordermanagement.application.command.CreateOrderCommand;
import com.lovecloud.ordermanagement.domain.Delivery;
import com.lovecloud.ordermanagement.domain.DeliveryStatus;
import com.lovecloud.ordermanagement.domain.Order;
import com.lovecloud.ordermanagement.domain.repository.OrderRepository;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCreateService {
    private final OrderRepository orderRepository;
    private final CoupleRepository coupleRepository;
    private final FundingRepository fundingRepository;
    public Long createOrder(CreateOrderCommand command) {
        Couple couple = coupleRepository.findByMemberIdOrThrow(command.userId());
        List<Funding> fundings = fundingRepository.findAllById(command.fundingIds());

        //각 펀딩에 대하여 모금이 완료된 상태인지 확인
        fundings.forEach(funding -> {
            if (!funding.getStatus().equals(FundingStatus.COMPLETED)) {
                throw new IllegalArgumentException("Funding is not complete");
            }
            //펀딩의 주인과 주문자가 같은 커플인지 확인
            if (!funding.getCouple().equals(couple)) {
                throw new IllegalArgumentException("Funding owner and orderer are not the same couple");
            }
        });

        //Delivery 생성
        Delivery delivery = Delivery.builder()
                .deliveryName(command.deliveryName())
                .receiverName(command.receiverName())
                .receiverPhoneNumber(command.receiverPhoneNumber())
                .zipCode(command.zipCode())
                .address(command.address())
                .detailAddress(command.detailAddress())
                .deliveryMemo(command.deliveryMemo())
                .deliveryStatus(DeliveryStatus.PENDING)
                .build();

        //Order 생성
        Order order = Order.builder()
                .couple(couple)
                .ordererName(command.ordererName())
                .ordererPhoneNumber(command.ordererPhoneNumber())
                .ordererMemo(command.ordererMemo())
                .delivery(delivery)
                .build();

        return orderRepository.save(order).getId();
    }
}
