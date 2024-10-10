package com.lovecloud.ordermanagement.application;

import com.lovecloud.blockchain.application.WalletPathResolver;
import com.lovecloud.blockchain.application.WeddingCrowdFundingService;
import com.lovecloud.blockchain.exception.FundingBlockchainException;
import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.FundingStatus;
import com.lovecloud.fundingmanagement.domain.repository.FundingRepository;
import com.lovecloud.global.util.DateUuidGenerator;
import com.lovecloud.ordermanagement.application.command.CreateOrderCommand;
import com.lovecloud.ordermanagement.domain.Delivery;
import com.lovecloud.ordermanagement.domain.Order;
import com.lovecloud.ordermanagement.domain.OrderDetails;
import com.lovecloud.ordermanagement.domain.repository.DeliveryRepository;
import com.lovecloud.ordermanagement.domain.repository.OrderDetailsRepository;
import com.lovecloud.ordermanagement.domain.repository.OrderRepository;
import com.lovecloud.ordermanagement.exception.DuplicateOrderFundingException;
import com.lovecloud.ordermanagement.exception.FundingNotCompletedException;
import com.lovecloud.ordermanagement.exception.MismatchedCoupleException;
import com.lovecloud.ordermanagement.exception.NoAvailableFundingsException;
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
public class OrderCreateService {
    private final OrderRepository orderRepository;
    private final CoupleRepository coupleRepository;
    private final FundingRepository fundingRepository;
    private final OrderDetailsRepository orderDetailsRepository;
    private final DeliveryRepository deliveryRepository;
    private final ProductOptionsRepository productOptionsRepository;
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

        validateFundings(fundings, couple);

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

    private void validateFundings(List<Funding> fundings, Couple couple) {
        if (fundings.isEmpty()) {
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
