package com.lovecloud.ordermanagement.application.validator;

import com.lovecloud.fundingmanagement.domain.Funding;
import com.lovecloud.fundingmanagement.domain.FundingStatus;
import com.lovecloud.ordermanagement.domain.DeliveryStatus;
import com.lovecloud.ordermanagement.domain.Order;
import com.lovecloud.ordermanagement.domain.OrderStatus;
import com.lovecloud.ordermanagement.domain.repository.OrderDetailsRepository;
import com.lovecloud.ordermanagement.exception.*;
import com.lovecloud.usermanagement.domain.Couple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderValidator {

    private final OrderDetailsRepository orderDetailsRepository;

    /**
     * (주문 생성시) 주문할 펀딩이 하나 이상 존재하는지 검증
     * */
    public void validateFundingsExistence(List<Funding> fundings) {
        if (fundings.isEmpty()) {
            throw new NoAvailableFundingsException();
        }
    }

    /**
     * (주문 생성시) 펀딩들에 대해 중복된 주문이 존재하는지 검증
     * */
    public void validateNoDuplicatedOrdersForFundings(List<Funding> fundings) {
        List<Long> fundingIds = fundings.stream()
                .map(Funding::getId)
                .collect(Collectors.toList());

        // 이미 주문된 펀딩 ID들을 조회
        List<Long> duplicatedFundingIds = orderDetailsRepository.findDuplicatedFundingIds(fundingIds);
        if (!duplicatedFundingIds.isEmpty()) {
            throw new DuplicateOrderFundingException();
        }
    }

    /**
     * (주문 생성시) 펀딩들이 모두 완료된 상태인지 검증
     * */
    public void validateFundingsCompletion(List<Funding> fundings) {
        boolean allCompleted = fundings.stream()
                .allMatch(funding -> funding.getStatus().equals(FundingStatus.COMPLETED));
        if (!allCompleted) {
            throw new FundingNotCompletedException();
        }
    }

    /**
     * (주문 생성시) 주문자와 펀딩들의 소유자가 일치하는지 검증
     * */
    public void validateFundingsOwnership(List<Funding> fundings, Couple couple) {
        boolean isOwner = fundings.stream()
                .allMatch(funding -> funding.getCouple().equals(couple));
        if (!isOwner) {
            throw new MismatchedCoupleException();
        }
    }

    /**
     * 주문 소유자와 커플이 일치하는지 검증
     */
    public void validateOrderOwnership(Order order, Couple couple) {
        if (!order.getCouple().getId().equals(couple.getId())) {
            throw new UnauthorizedOrderAccessException();
        }
    }

    /**
     * (주문 취소시) 이미 취소된 주문인지 검증
     */
    public void validateOrderNotCancelled(Order order) {
        if (order.getOrderStatus().equals(OrderStatus.CANCEL_REQUESTED) || order.getOrderStatus().equals(OrderStatus.CANCEL_COMPLETED)) {
            throw new OrderAlreadyCancelledException();
        }
    }

    /**
     * (주문 취소시) 배송시작 전인지 검증
     */
    public void validateDeliveryNotStarted(Order order) {
        if (!order.getDelivery().getDeliveryStatus().equals(DeliveryStatus.PENDING)) {
            throw new DeliveryAlreadyStartedException();
        }
    }
}
