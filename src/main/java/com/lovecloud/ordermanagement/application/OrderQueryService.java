package com.lovecloud.ordermanagement.application;

import com.lovecloud.ordermanagement.application.validator.OrderValidator;
import com.lovecloud.ordermanagement.domain.Order;
import com.lovecloud.ordermanagement.domain.OrderDetails;
import com.lovecloud.ordermanagement.domain.repository.OrderDetailsRepository;
import com.lovecloud.ordermanagement.domain.repository.OrderRepository;
import com.lovecloud.ordermanagement.exception.UnauthorizedOrderAccessException;
import com.lovecloud.ordermanagement.query.response.OrderDetailResponse;
import com.lovecloud.ordermanagement.query.response.OrderListResponse;
import com.lovecloud.productmanagement.domain.repository.MainImageRepository;
import com.lovecloud.usermanagement.domain.Couple;
import com.lovecloud.usermanagement.domain.repository.CoupleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderQueryService {
    private final OrderRepository orderRepository;
    private final OrderValidator orderValidator;
    private final CoupleRepository coupleRepository;

    public OrderDetailResponse findById(Long orderId, Long userId) {
        Order order = orderRepository.findByIdOrThrow(orderId);
        Couple couple = coupleRepository.findByMemberIdOrThrow(userId);
        //주문 작성자 검증
        orderValidator.validateOrderOwnership(order, couple);

        //주문한 상품들의 정보, 주문 정보를 OrderDetailResponse로 변환
        return OrderDetailResponse.from(order);
    }


    public List<OrderListResponse> findAllByUserId(Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);

        return OrderListResponse.from(orders);
    }

}
