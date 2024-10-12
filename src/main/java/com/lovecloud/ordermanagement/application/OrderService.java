package com.lovecloud.ordermanagement.application;

import com.lovecloud.blockchain.application.WeddingCrowdFundingService;
import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.repository.FundingRepository;
import com.lovecloud.global.util.DateUuidGenerator;
import com.lovecloud.ordermanagement.application.command.CreateOrderCommand;
import com.lovecloud.ordermanagement.application.validator.OrderValidator;
import com.lovecloud.ordermanagement.domain.*;
import com.lovecloud.ordermanagement.domain.repository.DeliveryRepository;
import com.lovecloud.ordermanagement.domain.repository.OrderDetailsRepository;
import com.lovecloud.ordermanagement.domain.repository.OrderRepository;
import com.lovecloud.productmanagement.domain.repository.ProductOptionsRepository;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CoupleRepository coupleRepository;
    private final FundingRepository fundingRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final DeliveryRepository deliveryRepository;
    private final ProductOptionsRepository productOptionsRepository;
    private final OrderValidator orderValidator;
    private final WeddingCrowdFundingService weddingCrowdFundingService;

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

    public Long createOrder(CreateOrderCommand command) {
        Couple couple = coupleRepository.findByMemberIdOrThrow(command.userId());
        List<Funding> fundings = fundingRepository.findAllById(command.fundingIds());

        // 주문할 펀딩이 하나 이상 존재하는지 검증
        orderValidator.validateFundingsExistence(fundings);
        // 펀딩들에 대해 중복된 주문이 존재하는지 검증
        orderValidator.validateNoDuplicatedOrdersForFundings(fundings);
        // 펀딩들이 모두 완료된 상태인지 검증
        orderValidator.validateFundingsCompletion(fundings);
        // 주문자와 펀딩들의 소유자가 일치하는지 검증
        orderValidator.validateFundingsOwnership(fundings, couple);

        Delivery delivery = createDelivery(command);
        delivery = deliveryRepository.save(delivery);

        Order order = createOrder(command, couple, delivery);
        order = orderRepository.save(order);

        List<OrderDetails> orderDetails = createOrderDetails(order, fundings);
        orderDetailsRepository.saveAll(orderDetails);

        // 주문한 상품들의 재고를 감소시키는 로직
        fundings.forEach(funding ->
                productOptionsRepository.findByIdWithLockOrThrow(funding.getProductOptions().getId()).decreaseStockQuantity());

        /**
         * TODO: 블록체인 연동
         * fundingBlockchainId가 정의되어야 함
         * */
//        try {
//            String walletFilePath = WalletPathResolver.resolveWalletPath(couple.getWallet().getKeyfile());
//            // 블록체인 연동 - 주문 완료
//            for (Funding funding : fundings) {
//                String transactionHash = weddingCrowdFundingService.completeOrder(walletFilePath, funding.getBlockcainId());
//                log.info("블록체인 트랜잭션 해시: {}", transactionHash);
//            }
//        } catch (Exception e) {
//            throw new FundingBlockchainException("블록체인 연동 중 오류가 발생하였습니다.");
//        }


        return order.getId();
    }

    public void cancelOrder(Long orderId, Long id) {
        Order order = orderRepository.findByIdOrThrow(orderId);
        Couple couple = coupleRepository.findByMemberIdOrThrow(id);

        //주문한 사용자와 주문한 커플이 일치하는지 검증
        orderValidator.validateOrderOwnership(order, couple);
        //취소되지 않은 주문인지 검증
        orderValidator.validateOrderNotCancelled(order);
        //배송시작 전인지 검증
        orderValidator.validateDeliveryNotStarted(order);

        //주문 취소. 주문 취소시 주문 상태를 취소요청으로 변경
        order.cancel();

        //재고 수량 복구
        order.getOrderDetails().forEach(orderDetails -> {
            orderDetails.getFunding().getProductOptions().increaseStockQuantity();
        });

        // TODO: 블록체인 연동

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
}
