package com.lovecloud.ordermanagement.application;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.FundingStatus;
import com.lovecloud.fundingmanagement.domain.repository.FundingRepository;
import com.lovecloud.global.util.DateUuidGenerator;
import com.lovecloud.ordermanagement.application.command.CreateOrderCommand;
import com.lovecloud.ordermanagement.domain.Delivery;
import com.lovecloud.ordermanagement.domain.DeliveryStatus;
import com.lovecloud.ordermanagement.domain.Order;
import com.lovecloud.ordermanagement.domain.OrderDetails;
import com.lovecloud.ordermanagement.domain.repository.DeliveryRepository;
import com.lovecloud.ordermanagement.domain.repository.OrderDetailsRepository;
import com.lovecloud.ordermanagement.domain.repository.OrderRepository;
import com.lovecloud.ordermanagement.exception.DuplicateOrderFundingException;
import com.lovecloud.ordermanagement.exception.FundingNotCompletedException;
import com.lovecloud.ordermanagement.exception.MismatchedCoupleException;
import com.lovecloud.ordermanagement.exception.NoAvailableFundingsException;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderCreateService {
    private final OrderRepository orderRepository;
    private final CoupleRepository coupleRepository;
    private final FundingRepository fundingRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final DeliveryRepository deliveryRepository;
    public Long createOrder(CreateOrderCommand command) {
        Couple couple = coupleRepository.findByMemberIdOrThrow(command.userId());
        List<Funding> fundings = fundingRepository.findAllById(command.fundingIds());

        validateFundings(fundings, couple);

        Delivery delivery = createDelivery(command);
        delivery = deliveryRepository.save(delivery);

        Order order = createOrder(command, couple, delivery);
        order = orderRepository.save(order);

        List<OrderDetails> orderDetails = createOrderDetails(order, fundings);
        orderDetailsRepository.saveAll(orderDetails);

        return order.getId();
    }

    private List<OrderDetails> createOrderDetails(Order order, List<Funding> fundings) {
        return fundings.stream()
                .map(funding -> OrderDetails.builder()
                        .order(order)
                        .funding(funding)
                        .build())
                .toList();
    }

    private Order createOrder(CreateOrderCommand command, Couple couple, Delivery delivery) {
        return Order.builder()
                .orderNumber(DateUuidGenerator.generateDateUuid())
                .couple(couple)
                .ordererName(command.ordererName())
                .ordererPhoneNumber(command.ordererPhoneNumber())
                .ordererMemo(command.ordererMemo())
                .orderDateTime(LocalDateTime.now())
                .delivery(delivery)
                .build();
    }

    private static Delivery createDelivery(CreateOrderCommand command) {
        return Delivery.builder()
                .deliveryName(command.deliveryName())
                .receiverName(command.receiverName())
                .receiverPhoneNumber(command.receiverPhoneNumber())
                .zipCode(command.zipCode())
                .address(command.address())
                .detailAddress(command.detailAddress())
                .deliveryMemo(command.deliveryMemo())
                .build();
    }

    private void validateFundings(List<Funding> fundings, Couple couple) {
        if(fundings.isEmpty()){
            throw new NoAvailableFundingsException();
        }
        for (Funding funding : fundings) {
            validateFundingStatus(funding);
            validateFundingCouple(funding, couple);
            validateDuplicateOrderDetails(funding);
        }

    }

    private void validateDuplicateOrderDetails(Funding funding) {
        //이미 주문된 펀딩인지 확인
        if (orderDetailsRepository.existsByFundingId(funding.getId())) {
            throw new DuplicateOrderFundingException();
        }
    }

    private void validateFundingStatus(Funding funding) {
        if (!funding.getStatus().equals(FundingStatus.COMPLETED)) {
            throw new FundingNotCompletedException();
        }
    }

    private void validateFundingCouple(Funding funding, Couple couple) {
        if (!funding.getCouple().equals(couple)) {
            throw new MismatchedCoupleException();
        }
    }
}
